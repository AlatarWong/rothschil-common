package io.github.rothschil.common.base.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.data.repository.core.RepositoryInformation;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import java.io.Serializable;

@SuppressWarnings({"rawtypes","unchecked"})
public class BaseRepositoryFactoryBean<R extends JpaRepository<T, ID>, T,ID extends Serializable> extends JpaRepositoryFactoryBean<R, T, ID> {


    public BaseRepositoryFactoryBean(Class<? extends R> repositoryInterface) {
        super(repositoryInterface);
    }

    @Override
	protected RepositoryFactorySupport createRepositoryFactory(EntityManager entityManager) {
		return new BaseRepositoryFactory(entityManager);
	}

	private static class BaseRepositoryFactory<T,I extends Serializable> extends JpaRepositoryFactory{

		private final EntityManager em;

        public BaseRepositoryFactory(EntityManager em) {
            super(em);
            this.em = em;
        }


//         /**
//         * 设置具体的实现类是BaseRepositoryImpl
//         * @param information
//         * @return
//         */
//        @Override
//        protected JpaRepositoryImplementation<?, ?> getTargetRepository(RepositoryInformation information, EntityManager entityManager) {
//            JpaEntityInformation<?, Serializable> entityInformation = this.getEntityInformation(information.getDomainType());
//            Object repository = this.getTargetRepositoryViaReflection(information, entityInformation, entityManager);
//            Assert.isInstanceOf(BaseRepositoryImpl.class, repository);
//            return (JpaRepositoryImplementation) repository;
////                return new BaseRepositoryImpl<T, I>((Class<T>) information.getDomainType(), em);
//        }

//        @Override
//        protected JpaRepositoryImplementation<?, ?> getTargetRepository(RepositoryInformation information, EntityManager entityManager) {
//            return new BaseRepositoryImpl(information.getDomainType(), em);
//        }

        @Override
        protected JpaRepositoryImplementation<?, ?> getTargetRepository(RepositoryInformation information, EntityManager entityManager) {
            return new BaseRepositoryImpl((Class<T>) information.getDomainType(), em);
        }

//        /**
//         * 设置具体的实现类是BaseRepositoryImpl
//         * @param information
//         * @return
//         */
//        protected Object getTargetRepository(RepositoryInformation information) {
//            return new BaseRepositoryImpl<T, I>((Class<T>) information.getDomainType(), em);
//        }

        /**
         *  设置具体的实现类的class
         * @param metadata
         * @return
         */
        @Override
        protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
            return BaseRepositoryImpl.class;
        }
	}

}
