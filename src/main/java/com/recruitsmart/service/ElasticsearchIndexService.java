package com.recruitsmart.service;

import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.recruitsmart.domain.*;
import com.recruitsmart.repository.*;
import com.recruitsmart.repository.search.*;
import org.elasticsearch.indices.IndexAlreadyExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.ManyToMany;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class ElasticsearchIndexService {

    private static final Lock reindexLock = new ReentrantLock();

    private final Logger log = LoggerFactory.getLogger(ElasticsearchIndexService.class);

    private final ActivityRepository activityRepository;

    private final ActivitySearchRepository activitySearchRepository;

    private final ActivityActionRepository activityActionRepository;

    private final ActivityActionSearchRepository activityActionSearchRepository;

    private final AddressRepository addressRepository;

    private final AddressSearchRepository addressSearchRepository;

    private final ApplicantRepository applicantRepository;

    private final ApplicantSearchRepository applicantSearchRepository;

    private final ApplicantCommentRepository applicantCommentRepository;

    private final ApplicantCommentSearchRepository applicantCommentSearchRepository;

    private final ApplicantInternalCommentRepository applicantInternalCommentRepository;

    private final ApplicantInternalCommentSearchRepository applicantInternalCommentSearchRepository;

    private final ApplicantStatusRepository applicantStatusRepository;

    private final ApplicantStatusSearchRepository applicantStatusSearchRepository;

    private final BookRepository bookRepository;

    private final BookSearchRepository bookSearchRepository;

    private final CompanyRepository companyRepository;

    private final CompanySearchRepository companySearchRepository;

    private final CompanyCommentRepository companyCommentRepository;

    private final CompanyCommentSearchRepository companyCommentSearchRepository;

    private final CompanyInternalCommentRepository companyInternalCommentRepository;

    private final CompanyInternalCommentSearchRepository companyInternalCommentSearchRepository;

    private final HiringContactRepository hiringContactRepository;

    private final HiringContactSearchRepository hiringContactSearchRepository;

    private final HiringContactCommentRepository hiringContactCommentRepository;

    private final HiringContactCommentSearchRepository hiringContactCommentSearchRepository;

    private final HiringContactInternalCommentRepository hiringContactInternalCommentRepository;

    private final HiringContactInternalCommentSearchRepository hiringContactInternalCommentSearchRepository;

    private final HotListRepository hotListRepository;

    private final HotListSearchRepository hotListSearchRepository;

    private final HotListBucketRepository hotListBucketRepository;

    private final HotListBucketSearchRepository hotListBucketSearchRepository;

    private final JobOrderRepository jobOrderRepository;

    private final JobOrderSearchRepository jobOrderSearchRepository;

    private final JobOrderCommentRepository jobOrderCommentRepository;

    private final JobOrderCommentSearchRepository jobOrderCommentSearchRepository;

    private final JobOrderInternalCommentRepository jobOrderInternalCommentRepository;

    private final JobOrderInternalCommentSearchRepository jobOrderInternalCommentSearchRepository;

    private final SkillRepository skillRepository;

    private final SkillSearchRepository skillSearchRepository;

    private final SkillCategoryRepository skillCategoryRepository;

    private final SkillCategorySearchRepository skillCategorySearchRepository;

    private final WorkHistoryRepository workHistoryRepository;

    private final WorkHistorySearchRepository workHistorySearchRepository;

    private final WorkStatusRepository workStatusRepository;

    private final WorkStatusSearchRepository workStatusSearchRepository;

    private final UserRepository userRepository;

    private final UserSearchRepository userSearchRepository;

    private final ElasticsearchTemplate elasticsearchTemplate;

    public ElasticsearchIndexService(
        UserRepository userRepository,
        UserSearchRepository userSearchRepository,
        ActivityRepository activityRepository,
        ActivitySearchRepository activitySearchRepository,
        ActivityActionRepository activityActionRepository,
        ActivityActionSearchRepository activityActionSearchRepository,
        AddressRepository addressRepository,
        AddressSearchRepository addressSearchRepository,
        ApplicantRepository applicantRepository,
        ApplicantSearchRepository applicantSearchRepository,
        ApplicantCommentRepository applicantCommentRepository,
        ApplicantCommentSearchRepository applicantCommentSearchRepository,
        ApplicantInternalCommentRepository applicantInternalCommentRepository,
        ApplicantInternalCommentSearchRepository applicantInternalCommentSearchRepository,
        ApplicantStatusRepository applicantStatusRepository,
        ApplicantStatusSearchRepository applicantStatusSearchRepository,
        BookRepository bookRepository,
        BookSearchRepository bookSearchRepository,
        CompanyRepository companyRepository,
        CompanySearchRepository companySearchRepository,
        CompanyCommentRepository companyCommentRepository,
        CompanyCommentSearchRepository companyCommentSearchRepository,
        CompanyInternalCommentRepository companyInternalCommentRepository,
        CompanyInternalCommentSearchRepository companyInternalCommentSearchRepository,
        HiringContactRepository hiringContactRepository,
        HiringContactSearchRepository hiringContactSearchRepository,
        HiringContactCommentRepository hiringContactCommentRepository,
        HiringContactCommentSearchRepository hiringContactCommentSearchRepository,
        HiringContactInternalCommentRepository hiringContactInternalCommentRepository,
        HiringContactInternalCommentSearchRepository hiringContactInternalCommentSearchRepository,
        HotListRepository hotListRepository,
        HotListSearchRepository hotListSearchRepository,
        HotListBucketRepository hotListBucketRepository,
        HotListBucketSearchRepository hotListBucketSearchRepository,
        JobOrderRepository jobOrderRepository,
        JobOrderSearchRepository jobOrderSearchRepository,
        JobOrderCommentRepository jobOrderCommentRepository,
        JobOrderCommentSearchRepository jobOrderCommentSearchRepository,
        JobOrderInternalCommentRepository jobOrderInternalCommentRepository,
        JobOrderInternalCommentSearchRepository jobOrderInternalCommentSearchRepository,
        SkillRepository skillRepository,
        SkillSearchRepository skillSearchRepository,
        SkillCategoryRepository skillCategoryRepository,
        SkillCategorySearchRepository skillCategorySearchRepository,
        WorkHistoryRepository workHistoryRepository,
        WorkHistorySearchRepository workHistorySearchRepository,
        WorkStatusRepository workStatusRepository,
        WorkStatusSearchRepository workStatusSearchRepository,
        ElasticsearchTemplate elasticsearchTemplate) {
        this.userRepository = userRepository;
        this.userSearchRepository = userSearchRepository;
        this.activityRepository = activityRepository;
        this.activitySearchRepository = activitySearchRepository;
        this.activityActionRepository = activityActionRepository;
        this.activityActionSearchRepository = activityActionSearchRepository;
        this.addressRepository = addressRepository;
        this.addressSearchRepository = addressSearchRepository;
        this.applicantRepository = applicantRepository;
        this.applicantSearchRepository = applicantSearchRepository;
        this.applicantCommentRepository = applicantCommentRepository;
        this.applicantCommentSearchRepository = applicantCommentSearchRepository;
        this.applicantInternalCommentRepository = applicantInternalCommentRepository;
        this.applicantInternalCommentSearchRepository = applicantInternalCommentSearchRepository;
        this.applicantStatusRepository = applicantStatusRepository;
        this.applicantStatusSearchRepository = applicantStatusSearchRepository;
        this.bookRepository = bookRepository;
        this.bookSearchRepository = bookSearchRepository;
        this.companyRepository = companyRepository;
        this.companySearchRepository = companySearchRepository;
        this.companyCommentRepository = companyCommentRepository;
        this.companyCommentSearchRepository = companyCommentSearchRepository;
        this.companyInternalCommentRepository = companyInternalCommentRepository;
        this.companyInternalCommentSearchRepository = companyInternalCommentSearchRepository;
        this.hiringContactRepository = hiringContactRepository;
        this.hiringContactSearchRepository = hiringContactSearchRepository;
        this.hiringContactCommentRepository = hiringContactCommentRepository;
        this.hiringContactCommentSearchRepository = hiringContactCommentSearchRepository;
        this.hiringContactInternalCommentRepository = hiringContactInternalCommentRepository;
        this.hiringContactInternalCommentSearchRepository = hiringContactInternalCommentSearchRepository;
        this.hotListRepository = hotListRepository;
        this.hotListSearchRepository = hotListSearchRepository;
        this.hotListBucketRepository = hotListBucketRepository;
        this.hotListBucketSearchRepository = hotListBucketSearchRepository;
        this.jobOrderRepository = jobOrderRepository;
        this.jobOrderSearchRepository = jobOrderSearchRepository;
        this.jobOrderCommentRepository = jobOrderCommentRepository;
        this.jobOrderCommentSearchRepository = jobOrderCommentSearchRepository;
        this.jobOrderInternalCommentRepository = jobOrderInternalCommentRepository;
        this.jobOrderInternalCommentSearchRepository = jobOrderInternalCommentSearchRepository;
        this.skillRepository = skillRepository;
        this.skillSearchRepository = skillSearchRepository;
        this.skillCategoryRepository = skillCategoryRepository;
        this.skillCategorySearchRepository = skillCategorySearchRepository;
        this.workHistoryRepository = workHistoryRepository;
        this.workHistorySearchRepository = workHistorySearchRepository;
        this.workStatusRepository = workStatusRepository;
        this.workStatusSearchRepository = workStatusSearchRepository;
        this.elasticsearchTemplate = elasticsearchTemplate;
    }

    @Async
    @Timed
    public void reindexAll() {
        if (reindexLock.tryLock()) {
            try {
                reindexForClass(Activity.class, activityRepository, activitySearchRepository);
                reindexForClass(ActivityAction.class, activityActionRepository, activityActionSearchRepository);
                reindexForClass(Address.class, addressRepository, addressSearchRepository);
                reindexForClass(Applicant.class, applicantRepository, applicantSearchRepository);
                reindexForClass(ApplicantComment.class, applicantCommentRepository, applicantCommentSearchRepository);
                reindexForClass(ApplicantInternalComment.class, applicantInternalCommentRepository, applicantInternalCommentSearchRepository);
                reindexForClass(ApplicantStatus.class, applicantStatusRepository, applicantStatusSearchRepository);
                reindexForClass(Book.class, bookRepository, bookSearchRepository);
                reindexForClass(Company.class, companyRepository, companySearchRepository);
                reindexForClass(CompanyComment.class, companyCommentRepository, companyCommentSearchRepository);
                reindexForClass(CompanyInternalComment.class, companyInternalCommentRepository, companyInternalCommentSearchRepository);
                reindexForClass(HiringContact.class, hiringContactRepository, hiringContactSearchRepository);
                reindexForClass(HiringContactComment.class, hiringContactCommentRepository, hiringContactCommentSearchRepository);
                reindexForClass(HiringContactInternalComment.class, hiringContactInternalCommentRepository, hiringContactInternalCommentSearchRepository);
                reindexForClass(HotList.class, hotListRepository, hotListSearchRepository);
                reindexForClass(HotListBucket.class, hotListBucketRepository, hotListBucketSearchRepository);
                reindexForClass(JobOrder.class, jobOrderRepository, jobOrderSearchRepository);
                reindexForClass(JobOrderComment.class, jobOrderCommentRepository, jobOrderCommentSearchRepository);
                reindexForClass(JobOrderInternalComment.class, jobOrderInternalCommentRepository, jobOrderInternalCommentSearchRepository);
                reindexForClass(Skill.class, skillRepository, skillSearchRepository);
                reindexForClass(SkillCategory.class, skillCategoryRepository, skillCategorySearchRepository);
                reindexForClass(WorkHistory.class, workHistoryRepository, workHistorySearchRepository);
                reindexForClass(WorkStatus.class, workStatusRepository, workStatusSearchRepository);
                reindexForClass(User.class, userRepository, userSearchRepository);

                log.info("Elasticsearch: Successfully performed reindexing");
            } finally {
                reindexLock.unlock();
            }
        } else {
            log.info("Elasticsearch: concurrent reindexing attempt");
        }
    }

    @SuppressWarnings("unchecked")
    private <T, ID extends Serializable> void reindexForClass(Class<T> entityClass, JpaRepository<T, ID> jpaRepository,
                                                              ElasticsearchRepository<T, ID> elasticsearchRepository) {
        elasticsearchTemplate.deleteIndex(entityClass);
        try {
            elasticsearchTemplate.createIndex(entityClass);
        } catch (IndexAlreadyExistsException e) {
            // Do nothing. Index was already concurrently recreated by some other service.
        }
        elasticsearchTemplate.putMapping(entityClass);
        if (jpaRepository.count() > 0) {
            // if a JHipster entity field is the owner side of a many-to-many relationship, it should be loaded manually
            List<Method> relationshipGetters = Arrays.stream(entityClass.getDeclaredFields())
                .filter(field -> field.getType().equals(Set.class))
                .filter(field -> field.getAnnotation(ManyToMany.class) != null)
                .filter(field -> field.getAnnotation(ManyToMany.class).mappedBy().isEmpty())
                .filter(field -> field.getAnnotation(JsonIgnore.class) == null)
                .map(field -> {
                    try {
                        return new PropertyDescriptor(field.getName(), entityClass).getReadMethod();
                    } catch (IntrospectionException e) {
                        log.error("Error retrieving getter for class {}, field {}. Field will NOT be indexed",
                            entityClass.getSimpleName(), field.getName(), e);
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

            int size = 100;
            for (int i = 0; i <= jpaRepository.count() / size; i++) {
                Pageable page = new PageRequest(i, size);
                log.info("Indexing page {} of {}, size {}", i, jpaRepository.count() / size, size);
                Page<T> results = jpaRepository.findAll(page);
                results.map(result -> {
                    // if there are any relationships to load, do it now
                    relationshipGetters.forEach(method -> {
                        try {
                            // eagerly load the relationship set
                            ((Set) method.invoke(result)).size();
                        } catch (Exception ex) {
                            log.error(ex.getMessage());
                        }
                    });
                    return result;
                });
                elasticsearchRepository.save(results.getContent());
            }
        }
        log.info("Elasticsearch: Indexed all rows for {}", entityClass.getSimpleName());
    }
}
