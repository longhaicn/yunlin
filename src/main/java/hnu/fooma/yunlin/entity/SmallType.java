package hnu.fooma.yunlin.entity;

/**
 * Created by Fooma on 2016/4/26.
 */
public class SmallType {
    public int smallId;
    public String bigType;
    public String smallType;

    public SmallType() {
    }

    public SmallType(int smallId, String bigType, String smallType) {
        this.smallId = smallId;
        this.bigType = bigType;
        this.smallType = smallType;
    }
}
