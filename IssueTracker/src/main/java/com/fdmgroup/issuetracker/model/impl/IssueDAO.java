package com.fdmgroup.issuetracker.model.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
/**
 * 
 * @author N17JAV12
 * This class aids in persisting Issues to the database,
 * manipulating Issue data and retrieving Issues from database 
 */
public class IssueDAO {

	private static final String PERSISTENCE_UNIT_NAME = "IssueTracker";

	private static EntityManagerFactory factory;
	private static UserDAO userDAO;

	/**
	 * initializes the entity manager factory 
	 */
	public IssueDAO() {
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);

	}

	public EntityManager getEntityManager() {
		return factory.createEntityManager();
	}

	/**
	 * persists Issue to database
	 * @param issue
	 * @return boolean value indicating whether persistence was
	 * successful (true) or unsuccessful (false)
	 */
	public boolean addIssue(Issue issue) {
		EntityManager em = getEntityManager();
		EntityTransaction et = em.getTransaction();
		try {
			et.begin();
			em.persist(issue);
			et.commit();
		} catch (Exception e) {
			return false;
		} finally {
			em.close();
		}
		return true;
	}

	/**
	 * retrieves issue by id
	 * @param id
	 * @return Issue
	 */
	public Issue getIssue(int id) {
		EntityManager em = getEntityManager();
		Issue issue = em.find(Issue.class, id);
		em.close();
		return issue;
	}

	/**
	 * retrieves all Issues stored in database
	 * only General Admin should be allowed to view
	 * @return List of Issue objects
	 */
	public List<Issue> listAll() {
		TypedQuery<Issue> query = getEntityManager().createNamedQuery("Issue.findAll", Issue.class);
		return query.getResultList();
		// check that it is admin accessing this
	}

	/**
	 * retrieves list of Issues assigned to a particular department
	 * @param deptId
	 * @return List of Issue objects
	 */
	public List<Issue> listByDept(int deptId) {
		TypedQuery<Issue> query = getEntityManager().createNamedQuery("Issue.listDepts", Issue.class);
		return query.setParameter("assignedTo", deptId).getResultList();
		// access by assigned to
	}

	/**
	 * retrieves list of Issues submitted by a particular user
	 * @param userId
	 * @return List of Issue objects
	 */
	public List<Issue> listByUser(int userId) {
		TypedQuery<Issue> query = getEntityManager().createNamedQuery("Issue.listUserIssues", Issue.class);
		return query.setParameter("submittedBy", userId).getResultList();
	}

	/**
	 * updates an Issue entity's fields 
	 * @param issue
	 * @return boolean indicating whether persistence was successful
	 */
	public boolean updateIssue(Issue issue) {
		EntityManager em = getEntityManager();
		EntityTransaction et = em.getTransaction();
		try {
			et.begin();
			
			em.merge(issue);
			et.commit();
			return true;
		} finally {
			em.close();
		}
	}
	
	/**
	 * retrieves Department by ID
	 * @param deptId
	 * @return Department object
	 */
	public Department getDepartmentById(int deptId){
		EntityManager em = getEntityManager();
		Department dept = em.find(Department.class, deptId);
		em.close();
		return dept;
	}
	
	/**
	 * retrieves all Departments stored is DB
	 * @return List of Department objects
	 */
	public List<Department> listDepts() {
		TypedQuery<Department> query = getEntityManager().createNamedQuery("Department.listAll", Department.class);
		return query.getResultList();

	}
	
}





