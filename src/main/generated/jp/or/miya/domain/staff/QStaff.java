package jp.or.miya.domain.staff;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QStaff is a Querydsl query type for Staff
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QStaff extends EntityPathBase<Staff> {

    private static final long serialVersionUID = 269474607L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QStaff staff = new QStaff("staff");

    public final jp.or.miya.domain.QBaseTimeEntity _super = new jp.or.miya.domain.QBaseTimeEntity(this);

    public final SetPath<jp.or.miya.domain.file.AttachFile, jp.or.miya.domain.file.QAttachFile> attachFiles = this.<jp.or.miya.domain.file.AttachFile, jp.or.miya.domain.file.QAttachFile>createSet("attachFiles", jp.or.miya.domain.file.AttachFile.class, jp.or.miya.domain.file.QAttachFile.class, PathInits.DIRECT2);

    //inherited
    public final StringPath createdBy = _super.createdBy;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final StringPath engName = createString("engName");

    public final StringPath ext = createString("ext");

    public final StringPath id = createString("id");

    //inherited
    public final StringPath modifiedBy = _super.modifiedBy;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    public final StringPath name = createString("name");

    public final jp.or.miya.domain.QPeriod period;

    public final EnumPath<jp.or.miya.domain.staff.enums.Position> pos = createEnum("pos", jp.or.miya.domain.staff.enums.Position.class);

    public final StringPath pw = createString("pw");

    public final EnumPath<jp.or.miya.domain.staff.enums.Responsibility> res = createEnum("res", jp.or.miya.domain.staff.enums.Responsibility.class);

    public final EnumPath<jp.or.miya.domain.user.enums.Role> role = createEnum("role", jp.or.miya.domain.user.enums.Role.class);

    public final QTeam team;

    public final EnumPath<jp.or.miya.domain.staff.enums.Work> work = createEnum("work", jp.or.miya.domain.staff.enums.Work.class);

    public QStaff(String variable) {
        this(Staff.class, forVariable(variable), INITS);
    }

    public QStaff(Path<? extends Staff> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QStaff(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QStaff(PathMetadata metadata, PathInits inits) {
        this(Staff.class, metadata, inits);
    }

    public QStaff(Class<? extends Staff> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.period = inits.isInitialized("period") ? new jp.or.miya.domain.QPeriod(forProperty("period")) : null;
        this.team = inits.isInitialized("team") ? new QTeam(forProperty("team")) : null;
    }

}

