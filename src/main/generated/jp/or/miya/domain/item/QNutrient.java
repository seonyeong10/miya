package jp.or.miya.domain.item;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QNutrient is a Querydsl query type for Nutrient
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QNutrient extends EntityPathBase<Nutrient> {

    private static final long serialVersionUID = -1867606465L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QNutrient nutrient = new QNutrient("nutrient");

    public final NumberPath<Integer> caffeine = createNumber("caffeine", Integer.class);

    public final NumberPath<Long> calorie = createNumber("calorie", Long.class);

    public final NumberPath<Integer> carbohydrate = createNumber("carbohydrate", Integer.class);

    public final NumberPath<Integer> cholesterol = createNumber("cholesterol", Integer.class);

    public final NumberPath<Integer> fat = createNumber("fat", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QMenu menu;

    public final NumberPath<Integer> protein = createNumber("protein", Integer.class);

    public final NumberPath<Integer> saturFat = createNumber("saturFat", Integer.class);

    public final NumberPath<Integer> sodium = createNumber("sodium", Integer.class);

    public final NumberPath<Integer> sugar = createNumber("sugar", Integer.class);

    public final NumberPath<Integer> transFat = createNumber("transFat", Integer.class);

    public QNutrient(String variable) {
        this(Nutrient.class, forVariable(variable), INITS);
    }

    public QNutrient(Path<? extends Nutrient> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QNutrient(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QNutrient(PathMetadata metadata, PathInits inits) {
        this(Nutrient.class, metadata, inits);
    }

    public QNutrient(Class<? extends Nutrient> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.menu = inits.isInitialized("menu") ? new QMenu(forProperty("menu"), inits.get("menu")) : null;
    }

}

