package jp.or.miya.domain.item;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QGoods is a Querydsl query type for Goods
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QGoods extends EntityPathBase<Goods> {

    private static final long serialVersionUID = 1801756670L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QGoods goods = new QGoods("goods");

    public final QItem _super;

    //inherited
    public final ListPath<jp.or.miya.domain.file.AttachFile, jp.or.miya.domain.file.QAttachFile> attachFiles;

    // inherited
    public final jp.or.miya.domain.base.QCategory category;

    //inherited
    public final StringPath createdBy;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate;

    //inherited
    public final StringPath engName;

    //inherited
    public final StringPath etc;

    //inherited
    public final NumberPath<Long> id;

    //inherited
    public final StringPath modifiedBy;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate;

    //inherited
    public final StringPath name;

    // inherited
    public final jp.or.miya.domain.QPeriod period;

    //inherited
    public final StringPath pick;

    //inherited
    public final NumberPath<Integer> price;

    public final NumberPath<Integer> quantity = createNumber("quantity", Integer.class);

    //inherited
    public final StringPath season;

    public QGoods(String variable) {
        this(Goods.class, forVariable(variable), INITS);
    }

    public QGoods(Path<? extends Goods> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QGoods(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QGoods(PathMetadata metadata, PathInits inits) {
        this(Goods.class, metadata, inits);
    }

    public QGoods(Class<? extends Goods> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new QItem(type, metadata, inits);
        this.attachFiles = _super.attachFiles;
        this.category = _super.category;
        this.createdBy = _super.createdBy;
        this.createdDate = _super.createdDate;
        this.engName = _super.engName;
        this.etc = _super.etc;
        this.id = _super.id;
        this.modifiedBy = _super.modifiedBy;
        this.modifiedDate = _super.modifiedDate;
        this.name = _super.name;
        this.period = _super.period;
        this.pick = _super.pick;
        this.price = _super.price;
        this.season = _super.season;
    }

}

