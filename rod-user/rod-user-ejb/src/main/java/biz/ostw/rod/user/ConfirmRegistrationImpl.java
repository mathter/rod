package biz.ostw.rod.user;

import java.util.Date;
import java.util.UUID;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.mail.Session;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import biz.ostw.persistence.jpa.AbstractJPARepository;

/**
 * @author mathter
 */
@Remote(ConfirmRegistrationService.class)
@Stateless
public class ConfirmRegistrationImpl extends AbstractJPARepository implements ConfirmRegistrationService {
	@PersistenceContext(name = "biz.ostw.rod.user")
	private EntityManager em;

	@Resource(mappedName = "java:/mail/rod")
	private Session mailSession;

	@EJB
	private UserRepository userRepository;

	@Override
	@Transactional(TxType.REQUIRED)
	public ConfirmRegistration newInstance(User user) {
		ConfirmRegistration entity = new ConfirmRegistration();

		entity.setUuid(UUID.randomUUID());
		entity = this.put(entity);
		entity.setUser(user);
		entity.setDate(new Date());

		entity = this.em.merge(entity);

		return entity;
	}

	@Override
	@Transactional(TxType.REQUIRED)
	public void confirm(UUID uuid) {
		ConfirmRegistration confirmRegistration = this.get(ConfirmRegistration.class, uuid);

		if (confirmRegistration != null) {
			User user = confirmRegistration.getUser();

			if (user != null) {
				confirmRegistration.setComplete(true);
				user.getAccessInfo().setRegistered(true);

				this.put(confirmRegistration);
				this.put(user);
			} else {
				throw new IllegalStateException("There is no user for '" + uuid + "'!");
			}
		} else {
			throw new IllegalStateException("There is no entry for '" + uuid + "'!");
		}
	}

	@Override
	@Transactional(TxType.REQUIRED)
	public void remove(UUID uuid) {
		Query query = this.em.createNamedQuery("ConfirmRegistration_remove");
		query.setParameter("uuid", uuid);

		query.executeUpdate();
	}

	@Override
	protected EntityManager getEntityManager() {
		return this.em;
	}
}
