package jp.or.miya.domain.file;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAttachFile is a Querydsl query type for AttachFile
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAttachFile extends EntityPathBase<AttachFile> {

    private static final long serialVersionUID = 467789744L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAttachFile attachFile = new QAttachFile("attachFile");

    public final StringPath dir = createString("dir");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final jp.or.miya.domain.item.QItem item;

    public final StringPath name = createString("name");

    public final StringPath orgName = createString("orgName");

    public final NumberPath<Integer> seq = createNumber("seq", Integer.class);

    public final jp.or.miya.domain.staff.QStaff staff;

    public QAttachFile(String variable) {
        this(AttachFile.class, forVariable(variable), INITS);
    }

    public QAttachFile(Path<? extends AttachFile> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAttachFile(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAttachFile(PathMetadata metadata, PathInits inits) {
        this(AttachFile.class, metadata, inits);
    }

    public QAttachFile(Class<? extends AttachFile> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.item = inits.isInitialized("item") ? new jp.or.miya.domain.item.QItem(forProperty("item"), inits.get("item")) : null;
        this.staff = inits.isInitialized("staff") ? new jp.or.miya.domain.staff.QStaff(forProperty("staff"), inits.get("staff")) : null;
    }

}

