package ru.javawebinar.topjava.repository.jpa;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class JpaUserRepository implements UserRepository {

/*
    @Autowired
    private SessionFactory sessionFactory;

    private Session openSession() {
        return sessionFactory.getCurrentSession();
    }
*/

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public User save(User user) {
        if (user.isNew()) {
            em.persist(user);
            //save new entity

            return user;
        } else {
            return em.merge(user);
            //merge with entity that already exists
        }
    }

    @Override
    public User get(int id) {
        return em.find(User.class, id);
        //find user by id
    }

    @Override
    @Transactional
    public boolean delete(int id) {

/*      User ref = em.getReference(User.class, id);
        em.remove(ref);
        //reference - достает ссылку на объект, но не весь объект

        Query query = em.createQuery("DELETE FROM User u WHERE u.id=:id");
        //запросы пишутся на JPQL - язык запроса к ORM, где мы оперируем объектами, а не колонками БД
        return query.setParameter("id", id).executeUpdate() != 0;*/

        return em.createNamedQuery(User.DELETE)
                //задаем константу, в константе уже задаем операцию
                //константы задаются в нашем случае в User
                .setParameter("id", id)
                .executeUpdate() != 0;
    }

    @Override
    public User getByEmail(String email) {
        List<User> users = em.createNamedQuery(User.BY_EMAIL, User.class)
                .setParameter(1, email)
                .getResultList();
        return DataAccessUtils.singleResult(users);
    }

    @Override
    public List<User> getAll() {
        return em.createNamedQuery(User.ALL_SORTED, User.class).getResultList();
    }
}
