package io.github.rothschil.common.base.persistence.repository;

import io.github.rothschil.common.utils.ReflectUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.Query;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SuppressWarnings({"unchecked"})
public class BaseRepositoryImpl<T, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements BaseRepository<T, ID> {


	private ReflectUtil reflectUtil = new ReflectUtil();

//	private Utils utils = new Utils();

	private Class<T> clazz;

	//
	private final EntityManager entityManager;


	@Autowired(required = false)
	public BaseRepositoryImpl(JpaEntityInformation<T, ID> entityInformation, EntityManager em) {
		super(entityInformation, em);
		this.clazz = entityInformation.getJavaType();
		this.entityManager=em;
	}

	@Autowired(required = false)
	public BaseRepositoryImpl(Class<T> domainClass, EntityManager em) {
		super(domainClass, em);
		this.entityManager=em;
	}

	@Override
	public void delete(ID[] ids) {

	}

	@Override
	public void updateBySql(String sql, Object... args) {
		Query query = entityManager.createNativeQuery(sql);
		int i = 0;
		for(Object arg:args) {
			query.setParameter(++i,arg);
		}
		query.executeUpdate();
	}

	@Override
	public void updateByHql(String hql, Object... args) {
		Query query = entityManager.createQuery(hql);
		int i = 0;
		for(Object arg:args) {
			System.out.println(arg);
			query.setParameter(++i,arg);
		}
		query.executeUpdate();
	}

	@Override
	public List<Object[]> listBySQL(String sql) {
		return entityManager.createNativeQuery(sql).getResultList();
	}



	/**
	 * @param tableMap 查询条件
	 * @return List
	 */
	@Override
	public List<T> findByConditions(Map<String, String> tableMap) {
		//调用省去map参数的方法
		Specification<T> specification = reflectUtil.createSpecification(tableMap, clazz, null);
		return this.findAll(specification);
	}


	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
	public void deleteValid(String ids) {
		List<String> strings = Arrays.asList(ids.split(","));
		if (!CollectionUtils.isEmpty(strings)) {
			//获取主键
			List<Field> idAnnoation = reflectUtil.getTargetAnnoation(clazz, Id.class);
			if (!CollectionUtils.isEmpty(idAnnoation)) {
				Field field = idAnnoation.get(0);
				strings.stream().forEach(id -> {
					T object = this.findOneByAttr(field.getName(), id);
					if (object != null) {
                        try {
                            reflectUtil.setValue(object, "valid", 0);
                        } catch (NoSuchFieldException | IllegalAccessException e) {
                            throw new RuntimeException(e);
                        }
                        this.save(object);
					}
				});
			}
		}
	}


	@Override
	public T findOneByAttr(String attr, String condition) {
		Specification<T> specification = reflectUtil.createOneSpecification(attr, condition);
		Optional<T> result = this.findOne(specification);

		if (result.isPresent()) {
			return result.get();
		} else {
			return null;
		}
	}

	@Override
	public List<T> findByAttr(String attr, String condition) {
		Specification<T> specification = reflectUtil.createOneSpecification(attr, condition);
		List<T> all = this.findAll(specification);
		return all;
	}

	@Override
	public List<T> findByAttrs(String attr, String conditions) {
		List<T> results = new ArrayList<>();
		if (!StringUtils.isEmpty(conditions)) {
			List<String> cons = Arrays.asList(conditions.split(","));
			cons.stream().forEach(condition -> {
				List<T> byAttr = findByAttr(attr, condition);
				if (byAttr != null) {
					results.addAll(byAttr);
				}
			});
		}
		return results;
	}


//	public Page<T> find(Class rootCls, CriteriaQuery<T> criteria, int pageNo, int pageSize) {
//
//		//count
//		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
//		CriteriaQuery criteriaC = builder.createQuery();
//		Root root = criteriaC.from(rootCls);
//		criteriaC.select(builder.count(root));
//		criteriaC.where(criteria.getRestriction());
//		List<Long> totals = entityManager.createQuery(criteriaC).getResultList();
//		Long total = 0L;
//		for (Long element : totals) {
//			total += element == null ? 0 : element;
//		}
//		//content
//		TypedQuery<T> query = entityManager.createQuery(criteria);
//		query.setFirstResult((pageNo - 1) * pageSize);
//		query.setMaxResults(pageSize);
//
//		List<T> content = total > query.getFirstResult() ? query.getResultList() : Collections.<T> emptyList();
//		Sort sort = new Sort(Sort.Direction.DESC, "id");
//		Pageable pageable = new PageRequest(pageNo, pageSize, sort);
//		Page<T> pageRst = new PageImpl<T>(content, pageable, total);
//		return pageRst;
//
//	}

//	public Page<T> find2(Class rootCls, CriteriaQuery<T> criteria, int pageNo, int pageSize){
//		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
//		//调用criteriaBuilder.createQuery来创建CriteriaQuery。其中createQuery的参数是Query返回值类型
//		CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
//		//调用criteriaQuery.from(Order.class)。参数是对应于order表的实体类，query.from
//		// 类似于sql中的from语句，该方法的执行等价于sql中的from order。
//		Root root = criteriaQuery.from(rootCls);
//		//调用 query.select创建映射。 query.select(criteriaBuilder.count(root.get(“id”)))
//		// 等价于select count(id)。如果执行query.select(root)则等价于select *。
//		criteriaQuery.select(root);
//
//		//查询条件
//		List<Predicate> predicates = new ArrayList<Predicate>();
//		//将Predicate放在 query.where中
//		criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
//		entityManager.createQuery(criteriaQuery) ;
//		Sort sort = new Sort(Sort.Direction.DESC, "id");
//		Pageable pageable = new PageRequest(pageNo, pageSize, sort);
//
//		List<Long> totals = entityManager.createQuery(criteriaQuery).getResultList();
//		Long total = 0L;
//		for (Long element : totals) {
//			total += element == null ? 0 : element;
//		}
//		return null;
//	}


//	public Page<T> findCriteria(Specification<T> spec, Pageable pageable){
//		  return super.findAll(spec,pageable);
//	}

//	public Page<T> find3(Class rootCls,int pageNo, int pageSize){
//		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
//		//调用criteriaBuilder.createQuery来创建CriteriaQuery。其中createQuery的参数是Query返回值类型
//		CriteriaQuery criteriaQuery = criteriaBuilder.createQuery();
//		//调用criteriaQuery.from(Order.class)。参数是对应于order表的实体类，query.from
//		// 类似于sql中的from语句，该方法的执行等价于sql中的from order。
//		Root root = criteriaQuery.from(rootCls);
//		//调用 query.select创建映射。 query.select(criteriaBuilder.count(root.get(“id”)))
//		// 等价于select count(id)。如果执行query.select(root)则等价于select *。
//		criteriaQuery.select(root);
//
//		//查询条件
//		List<Predicate> predicates = new ArrayList<Predicate>();
//		//将Predicate放在 query.where中
//		criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
//		entityManager.createQuery(criteriaQuery) ;
//		Sort sort = new Sort(Sort.Direction.DESC, "id");
//		Pageable pageable = new PageRequest(pageNo, pageSize, sort);
//
//		List<Long> totals = entityManager.createQuery(criteriaQuery).getResultList();
//		Long total = 0L;
//		for (Long element : totals) {
//			total += element == null ? 0 : element;
//		}
//
//		return null;
//	}



//
//	public int getPredicate(AbstractEntity<?> ae, int pageNo, int pageSize){
//		try {
//			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
//			CriteriaQuery criteriaQuery = criteriaBuilder.createQuery();
//			//查询条件
//			List<Predicate> predicates = new ArrayList<Predicate>();
//			Root root = criteriaQuery.from(ae.getClass());
//			MethodUtil.getFieldValue(ae,root,criteriaBuilder,predicates);
//
//			criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
//			TypedQuery<T> query = entityManager.createQuery(criteriaQuery);
//
//			query.setFirstResult((pageNo - 1) * pageSize);
//			query.setMaxResults(pageSize);
//
//			List<T> content = total > query.getFirstResult() ? query.getResultList() : Collections.<T> emptyList();
//
//			List<Long> totals = entityManager.createQuery(criteriaQuery).getResultList();
//			Long total = 0L;
//			for (Long element : totals) {
//				total += element == null ? 0 : element;
//			}
//
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		return 0;
//	}
}
