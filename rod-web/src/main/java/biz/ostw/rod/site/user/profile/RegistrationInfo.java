package biz.ostw.rod.site.user.profile;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import biz.ostw.rod.tools.PasswordGenerator;
import biz.ostw.rod.user.ConfirmRegistrationService;
import biz.ostw.rod.user.Role;
import biz.ostw.rod.user.User;
import biz.ostw.rod.user.UserRepository;
import biz.ostw.rod.user.channel.Channel;
import biz.ostw.rod.user.channel.ChannelService;
import biz.ostw.rod.user.channel.ChannelType;

/**
 * @author mathter
 */
@Named
@Dependent
public class RegistrationInfo {
	private static final Logger LOG = LoggerFactory.getLogger(RegistrationInfo.class);

	private static final Comparator<ChannelType> CHANNEL_TYPE_COMPARATOR = new Comparator<ChannelType>() {
		@Override
		public int compare(ChannelType left, ChannelType right) {
			return left.getName().compareTo(right.getName());
		}
	};

	@EJB
	private ChannelService channelService;

	@EJB
	private UserRepository userRepository;

	@EJB
	private ConfirmRegistrationService confirmRegistrationService;

	/* User */
	private User user;

	/* Authentication */
	private String login;

	private String password;

	private String repeatPassword;

	/* Channels */

	private Channel emailChannel;

	private List<Channel> channels;

	private List<ChannelType> channelTypes;

	@PostConstruct
	private void init() {
		Principal principal = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();

		if (principal == null) {
			LOG.debug("Init for new user");

			this.user = new User();
			this.channels = new ArrayList<>();
			this.emailChannel = this.getEmptyEmailChannel();
		} else {
			this.user = this.userRepository.get(principal.getName());
			this.login = user.getLogin();
			this.channels = this.channelService.get(this.user);

			Iterator<Channel> iterator = this.channels.iterator();
			ChannelType emailChannelType = this.channelService.getEmailChannelType();

			while (iterator.hasNext()) {
				Channel channel = iterator.next();

				if (emailChannelType.equals(channel.getChannelType())) {
					this.emailChannel = channel;
					iterator.remove();
					break;
				}

				if (this.emailChannel == null) {
					this.emailChannel = this.getEmptyEmailChannel();
				}
			}
		}

		this.channelTypes = this.channelService.geChannelTypes();
		this.channelTypes.remove(this.channelService.getEmailChannelType());

	}

	public void addChannel(ChannelType channelType, String chanelValue) {
		if (channelType != null && chanelValue != null) {
			Channel channel = new Channel();
			channel.setChannelType(channelType);
			channel.setValue(chanelValue);
			channel.setUser(this.user);

			this.channels.add(channel);
			this.channelTypes.remove(channelType);
		} else {
			LOG.warn("Bad parameters: channelType='{}', channelValue='{}'", channelType, chanelValue);
		}
	}

	public void removeChannel(Channel channel) {
		Iterator<Channel> iterator = this.channels.iterator();

		while (iterator.hasNext()) {
			Channel channel2 = iterator.next();

			if (channel.getChannelType().equals(channel2.getChannelType())) {
				iterator.remove();
				this.channelTypes.add(channel.getChannelType());

				if (channel2.getSystemId() != null) {
					this.channelService.remove(channel2);
				}
			}
			;
		}
	}

	@Transactional(TxType.REQUIRES_NEW)
	public String submite() throws Exception {
		try {
			User user;
			Role role = this.userRepository.getRegisteredRole();

			Principal principal = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();

			if (principal == null) {
				user = new User();
				user.setPassword(PasswordGenerator.generate(this.password));
			} else {
				user = this.userRepository.get(this.login);

				if (this.password != null && !this.password.isEmpty()) {
					user.setPassword(PasswordGenerator.generate(this.password));
				}
			}

			user.setLogin(this.login);
			user.setRoles(Collections.singleton(role));
			user = this.userRepository.put(user);

			this.emailChannel.setUser(user);
			this.channelService.put(this.emailChannel);

			for (Channel channel : this.channels) {
				channel.setUser(user);
				this.channelService.put(channel);
			}

			if (principal == null) {
				this.confirmRegistrationService.newInstance(user);
				return "confirmpage";
			} else {
				return "account";
			}

		} catch (Exception e) {
			LOG.error("Cant' create user!");
			throw e;
		}
	}

	public Channel getEmailChannel() {
		return emailChannel;
	}

	public void setEmailChannel(Channel emailChannel) {
		this.emailChannel = emailChannel;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRepeatPassword() {
		return repeatPassword;
	}

	public void setRepeatPassword(String repeatPassword) {
		this.repeatPassword = repeatPassword;
	}

	public List<Channel> getChannels() {
		return this.channels;
	}

	public List<ChannelType> getChannelTypes() {
		Collections.sort(this.channelTypes, CHANNEL_TYPE_COMPARATOR);

		return this.channelTypes;
	}

	private Channel getEmptyEmailChannel() {
		Channel channel = new Channel();
		channel.setChannelType(this.channelService.getEmailChannelType());

		return channel;
	}
}
