package jp.or.miya.domain.item;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QItem is a Querydsl query type for Item
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QItem extends EntityPathBase<Item> {

    private static final long serialVersionUID = 1997847915L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QItem item = new QItem("item");

    public final jp.or.miya.domain.QBaseTimeEntity _super = new jp.or.miya.domain.QBaseTimeEntity(this);

    public final ListPath<jp.or.miya.domain.file.AttachFile, jp.or.miya.domain.file.QAttachFile> attachFiles = this.<jp.or.miya.domain.file.AttachFile, jp.or.miya.domain.file.QAttachFile>createList("attachFiles", jp.or.miya.domain.file.AttachFile.class, jp.or.miya.domain.file.QAttachFile.class, PathInits.DIRECT2);

    public final jp.or.miya.domain.base.QCategory category;

    //inherited
    public final StringPath createdBy = _super.createdBy;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final StringPath engName = createString("engName");

    public final StringPath etc = createString("etc");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final StringPath modifiedBy = _super.modifiedBy;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    public final StringPath name = createString("name");

    public final jp.or.miya.domain.QPeriod period;

    public final StringPath pick = createString("pick");

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    public final StringPath season = createString("season");

    public QItem(String variable) {
        this(Item.class, forVariable(variable), INITS);
    }

    public QItem(Path<? extends Item> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QItem(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QItem(PathMetadata metadata, PathInits inits) {
        this(Item.class, metadata, inits);
    }

    public QItem(Class<? extends Item> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.category = inits.isInitialized("category") ? new jp.or.miya.domain.base.QCategory(forProperty("category"), inits.get("category")) : null;
        this.period = inits.isInitialized("period") ? new jp.or.miya.domain.QPeriod(forProperty("period")) : null;
    }

}

