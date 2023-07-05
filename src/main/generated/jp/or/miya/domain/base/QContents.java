package jp.or.miya.domain.base;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QContents is a Querydsl query type for Contents
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QContents extends EntityPathBase<Contents> {

    private static final long serialVersionUID = 1022065524L;

    public static final QContents contents = new QContents("contents");

    public final jp.or.miya.domain.QBaseTimeEntity _super = new jp.or.miya.domain.QBaseTimeEntity(this);

    public final StringPath accRole = createString("accRole");

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

    public final NumberPath<Long> pId = createNumber("pId", Long.class);

    public final NumberPath<Integer> seq = createNumber("seq", Integer.class);

    public final StringPath sort = createString("sort");

    public final StringPath url = createString("url");

    public final NumberPath<Integer> useYn = createNumber("useYn", Integer.class);

    public QContents(String variable) {
        super(Contents.class, forVariable(variable));
    }

    public QContents(Path<? extends Contents> path) {
        super(path.getType(), path.getMetadata());
    }

    public QContents(PathMetadata metadata) {
        super(Contents.class, metadata);
    }

}

