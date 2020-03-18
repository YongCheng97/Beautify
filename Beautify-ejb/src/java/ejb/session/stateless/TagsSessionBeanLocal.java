/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Tags;
import java.util.List;
import javax.ejb.Local;
import util.exception.CreateNewTagException;
import util.exception.DeleteTagException;
import util.exception.InputDataValidationException;
import util.exception.TagNotFoundException;
import util.exception.UpdateTagException;

/**
 *
 * @author fooyo
 */
@Local
public interface TagsSessionBeanLocal {

    public Tags createNewTagEntity(Tags newTagEntity) throws InputDataValidationException, CreateNewTagException;

    public List<Tags> retrieveAllTags();

    public Tags retrieveTagByTagId(Long tagId) throws TagNotFoundException;

    public void updateTag(Tags tagEntity) throws InputDataValidationException, TagNotFoundException, UpdateTagException;

    public void deleteTag(Long tagId) throws TagNotFoundException, DeleteTagException;
    
}
