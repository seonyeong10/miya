package jp.or.miya.domain.base;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QContents is a Querydsl query type for Contents
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QContents extends EntityPathBase<Contents> {

    private static final long serialVersionUID = 1022065524L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QContents contents = new QContents("contents");

    public final jp.or.miya.domain.QBaseTimeEntity _super = new jp.or.miya.domain.QBaseTimeEntity(this);

    public final StringPath accRole = createString("accRole");

    public final ListPath<Contents, QContents> children = this.<Contents, QContents>createList("children", Contents.class, QContents.class, PathInits.DIRECT2);

    //inherited
    public final StringPath createdBy = _super.createdBy;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final StringPath modifiedBy = _super.modifiedBy;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    public final StringPath name = createString("name");

    public final QContents parent;

    public final NumberPath<Integer> seq = createNumber("seq", Integer.class);

    public final StringPath sort = createString("sort");

    public final StringPath url = createString("url");

    public final NumberPath<Integer> useYn = createNumber("useYn", Integer.class);

    public QContents(String variable) {
        this(Contents.class, forVariable(variable), INITS);
    }

    public QContents(Path<? extends Contents> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QContents(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QContents(PathMetadata metadata, PathInits inits) {
        this(Contents.class, metadata, inits);
    }

    public QContents(Class<? extends Contents> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.parent = inits.isInitialized("parent") ? new QContents(forProperty("parent"), inits.get("parent")) : null;
    }

}

