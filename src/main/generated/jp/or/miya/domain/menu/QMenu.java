package jp.or.miya.domain.menu;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMenu is a Querydsl query type for Menu
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMenu extends EntityPathBase<Menu> {

    private static final long serialVersionUID = -1682617109L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMenu menu = new QMenu("menu");

    public final jp.or.miya.domain.QBaseTimeEntity _super = new jp.or.miya.domain.QBaseTimeEntity(this);

    public final SetPath<jp.or.miya.domain.file.AttachFile, jp.or.miya.domain.file.QAttachFile> attachFiles = this.<jp.or.miya.domain.file.AttachFile, jp.or.miya.domain.file.QAttachFile>createSet("attachFiles", jp.or.miya.domain.file.AttachFile.class, jp.or.miya.domain.file.QAttachFile.class, PathInits.DIRECT2);

    public final jp.or.miya.domain.base.QCategory category;

    //inherited
    public final StringPath createdBy = _super.createdBy;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final StringPath engName = createString("engName");

    public final StringPath expl = createString("expl");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final StringPath modifiedBy = _super.modifiedBy;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    public final StringPath name = createString("name");

    public final QNutrient nutrient;

    public final StringPath part = createString("part");

    public final jp.or.miya.domain.QPeriod period;

    public final NumberPath<Integer> pick = createNumber("pick", Integer.class);

    public final NumberPath<Long> price = createNumber("price", Long.class);

    public final NumberPath<Integer> season = createNumber("season", Integer.class);

    public final StringPath sizes = createString("sizes");

    public final StringPath temp = createString("temp");

    public QMenu(String variable) {
        this(Menu.class, forVariable(variable), INITS);
    }

    public QMenu(Path<? extends Menu> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMenu(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMenu(PathMetadata metadata, PathInits inits) {
        this(Menu.class, metadata, inits);
    }

    public QMenu(Class<? extends Menu> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.category = inits.isInitialized("category") ? new jp.or.miya.domain.base.QCategory(forProperty("category"), inits.get("category")) : null;
        this.nutrient = inits.isInitialized("nutrient") ? new QNutrient(forProperty("nutrient"), inits.get("nutrient")) : null;
        this.period = inits.isInitialized("period") ? new jp.or.miya.domain.QPeriod(forProperty("period")) : null;
    }

}

