package ejb.session.stateless;

import entity.Tags;
import java.util.List;
import java.util.Set;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import util.exception.CreateNewTagException;
import util.exception.DeleteTagException;
import util.exception.InputDataValidationException;
import util.exception.TagNotFoundException;
import util.exception.UpdateTagException;

@Stateless
@Local(TagsSessionBeanLocal.class)

public class TagsSessionBean implements TagsSessionBeanLocal {

    @PersistenceContext(unitName = "Beautify-ejbPU")
    private EntityManager em;

    private final ValidatorFactory validatorFactory;
    private final Validator validator;

    public TagsSessionBean() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Override
    public Tags createNewTagEntity(Tags newTagEntity) throws InputDataValidationException, CreateNewTagException {
        Set<ConstraintViolation<Tags>> constraintViolations = validator.validate(newTagEntity);

        if (constraintViolations.isEmpty()) {
            try {
                em.persist(newTagEntity);
                em.flush();

                return newTagEntity;
            } catch (PersistenceException ex) {
                if (ex.getCause() != null
                        && ex.getCause().getCause() != null
                        && ex.getCause().getCause().getClass().getSimpleName().equals("SQLIntegrityConstraintViolationException")) {
                    throw new CreateNewTagException("Tag with same name already exist");
                } else {
                    throw new CreateNewTagException("An unexpected error has occurred: " + ex.getMessage());
                }
            } catch (Exception ex) {
                throw new CreateNewTagException("An unexpected error has occurred: " + ex.getMessage());
            }
        } else {
            throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
        }
    }

    @Override
    public List<Tags> retrieveAllTags() {
        Query query = em.createQuery("SELECT t FROM Tags t ORDER BY t.name ASC");
        List<Tags> tagEntities = query.getResultList();

        for (Tags tagEntity : tagEntities) {
            tagEntity.getProducts().size();
            tagEntity.getServiceProviders().size();
        }

        return tagEntities;
    }

    @Override
    public Tags retrieveTagByTagId(Long tagId) throws TagNotFoundException {
        Tags tagEntity = em.find(Tags.class, tagId);

        if (tagEntity != null) {
            return tagEntity;
        } else {
            throw new TagNotFoundException("Tag ID " + tagId + " does not exist!");
        }
    }

    @Override
    public void updateTag(Tags tagEntity) throws InputDataValidationException, TagNotFoundException, UpdateTagException {
        Set<ConstraintViolation<Tags>> constraintViolations = validator.validate(tagEntity);

        if (constraintViolations.isEmpty()) {
            if (tagEntity.getTagId() != null) {
                Tags tagEntityToUpdate = retrieveTagByTagId(tagEntity.getTagId());

                Query query = em.createQuery("SELECT t FROM Tags t WHERE t.name = :inName AND t.tagId <> :inTagId");
                query.setParameter("inName", tagEntity.getName());
                query.setParameter("inTagId", tagEntity.getTagId());

                if (!query.getResultList().isEmpty()) {
                    throw new UpdateTagException("The name of the tag to be updated is duplicated");
                }

                tagEntityToUpdate.setName(tagEntity.getName());
            } else {
                throw new TagNotFoundException("Tag ID not provided for tag to be updated");
            }
        } else {
            throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
        }
    }

    @Override
    public void deleteTag(Long tagId) throws TagNotFoundException, DeleteTagException {
        Tags tagEntityToRemove = retrieveTagByTagId(tagId);

        if (!tagEntityToRemove.getProducts().isEmpty()) {
            throw new DeleteTagException("Tag ID " + tagId + " is associated with existing products and cannot be deleted!");
        } else {
            em.remove(tagEntityToRemove);
        }
    }

    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<Tags>> constraintViolations) {
        String msg = "Input data validation error!:";

        for (ConstraintViolation constraintViolation : constraintViolations) {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }

        return msg;
    }
}
