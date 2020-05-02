package ws.datamodel;

import entity.Tag;
import java.util.List;

public class RetrieveAllTagsRsp {

    private List<Tag> tagEntities;

    public RetrieveAllTagsRsp() {
    }

    public RetrieveAllTagsRsp(List<Tag> tagEntities) {
        this.tagEntities = tagEntities;
    }

    public List<Tag> getTagEntities() {
        return tagEntities;
    }

    public void setTagEntities(List<Tag> tagEntities) {
        this.tagEntities = tagEntities;
    }
}
