package biz.ostw.ee.vfs;

import javax.ejb.EJB;

import biz.ostw.rod.user.User;
import biz.ostw.rod.user.UserRepository;

/**
 * @author mathter
 */
public class UserServiceImpl implements VfsUserService {

	@EJB
	private UserRepository userRepository;

	@Override
	public Long getUserId(String login) {
		User user = this.userRepository.get(login);

		return user != null ? user.getSystemId() : null;
	}

	@Override
	public Long getGroupId(String groupName) {
		return null;
	}
}
