package hnu.fooma.yunlin.entity;

/**
 * Created by Fooma on 2016/5/1.
 */
public class Collection {
    public int collectionId;
    public int gMemberId;
    public int goodsId;
    public int memberId;
    public Collection() {
    }

    public Collection(int collectionId, int goodsId, int memberId) {
        this.collectionId = collectionId;
        this.goodsId = goodsId;
        this.memberId = memberId;
    }
}
