package jp.or.miya.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QPeriod is a Querydsl query type for Period
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QPeriod extends BeanPath<Period> {

    private static final long serialVersionUID = -1946356028L;

    public static final QPeriod period = new QPeriod("period");

    public final DateTimePath<java.time.LocalDateTime> endDate = createDateTime("endDate", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> startDate = createDateTime("startDate", java.time.LocalDateTime.class);

    public QPeriod(String variable) {
        super(Period.class, forVariable(variable));
    }

    public QPeriod(Path<? extends Period> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPeriod(PathMetadata metadata) {
        super(Period.class, metadata);
    }

}

