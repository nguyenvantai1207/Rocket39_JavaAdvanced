package com.vti.backend;

import com.vti.entity.Group;
import lombok.NoArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import utils.HibernateUtils;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class DepartmentRepository {
    public void createGroups(Group group) {
        Session session = null;
        SessionFactory sessionFactory = HibernateUtils.buildSessionFactory();
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            session.save(group);
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                HibernateUtils.close();
            }
        }
    }

    public List<Group> getAllGroups() {
        List<Group> groups = new ArrayList<>();
        Session session = null;
        SessionFactory sessionFactory = HibernateUtils.buildSessionFactory();

        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            String hql = "from Group";

            Query<Group> query = session.createQuery(hql, Group.class);
            groups = query.list();

            session.getTransaction().commit();
        } catch (Exception e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            HibernateUtils.close();
        }
        return groups;
    }

    public Group findGroupById(int id) {
        Session session = null;
        Group group = new Group();
        SessionFactory sessionFactory = HibernateUtils.buildSessionFactory();

        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            group = session.get(Group.class, id);
            session.getTransaction().commit();

        } catch (Exception e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
        } finally {
            HibernateUtils.close();
        }
        return group;
    }

    public Group findGroupByName(String groupName) {
        Session session = null;
        Group group = new Group();
        SessionFactory sessionFactory = HibernateUtils.buildSessionFactory();

        try {
            session = sessionFactory.openSession();
            session.beginTransaction();

            String hql = "FROM Group WHERE name = :groupName";
            Query<Group> query = session.createQuery(hql, Group.class);
            query.setParameter("groupName", groupName);

            group = query.uniqueResult();

            session.getTransaction().commit();
        } catch (Exception e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
        } finally {
            HibernateUtils.close();
        }
        return group;
    }

    public void updateGroup(Group updatedGroup) {
        Session session = null;
        SessionFactory sessionFactory = HibernateUtils.buildSessionFactory();
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();

            Group existingGroup = session.get(Group.class, updatedGroup.getId());
            if (existingGroup != null) {
                existingGroup.setName(updatedGroup.getName());

                session.update(existingGroup);
            } else {
                System.err.println("Group not found for Id: " + updatedGroup.getId());
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
        } finally {
            HibernateUtils.close();
        }
    }

    public void deleteGroupById(int id) {
        Session session = null;
        SessionFactory sessionFactory = HibernateUtils.buildSessionFactory();
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();

            Group existingGroup = session.get(Group.class, id);

            if (existingGroup != null) {
                session.delete(existingGroup);
            } else {
                System.err.println("Group not found for Id: " + id);
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
        } finally {
            HibernateUtils.close();
        }
    }

    public Boolean checkGroupById(int id)
    {
        Session session = null;
        SessionFactory sessionFactory = HibernateUtils.buildSessionFactory();
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();

            Group checkedGroup = session.get(Group.class, id);

            if (checkedGroup != null) {
                return true;
            } else {
                System.err.println("Group not found for Id: " + id);
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
        } finally {
            HibernateUtils.close();
        }
        return false;
    }

    public Boolean checkGroupByName(String groupName)
    {
        Session session = null;
        Boolean isExisted;
        SessionFactory sessionFactory = HibernateUtils.buildSessionFactory();
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();

            String hql = "FROM Group WHERE name = :groupName";
            Query<Group> query = session.createQuery(hql, Group.class);
            query.setParameter("groupName", groupName);

            Group checkedgroup = query.uniqueResult();

            if(checkedgroup.getName().equals(groupName))
            {
                System.out.println("Found Successfully!");
                return true;
            }else{
                System.err.println("Group not found for Id: " + groupName);
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
        } finally {
            HibernateUtils.close();
        }
        return false;
    }
}
