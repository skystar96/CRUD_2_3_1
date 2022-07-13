package hiber.dao;

import hiber.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

   @PersistenceContext
   private EntityManager entityManager;

   @Override
   public void add(User user) {
      entityManager.persist(user);
   }

   @Override
   public void removeUser(Long id) {
      entityManager.remove(getUser(id));
   }

   @Override
   public User getUser(Long id) {
      return entityManager.find(User.class,id);
   }

   @Override
   public User editUser(long id, User updated) {
      User user = entityManager.find(User.class,id);
      user.setId(updated.getId());
      user.setFirstName(updated.getFirstName());
      user.setLastName(updated.getLastName());
      user.setEmail(updated.getEmail());
      entityManager.merge(user);
      return user;
   }

   @Override
   public List<User> listUsers() {
      return entityManager.createQuery("select user from User user", User.class).getResultList();
   }

}
