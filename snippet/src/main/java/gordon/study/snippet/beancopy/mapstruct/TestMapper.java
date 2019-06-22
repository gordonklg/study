package gordon.study.snippet.beancopy.mapstruct;

import gordon.study.snippet.beancopy.model.ModelB;
import gordon.study.snippet.beancopy.model.ModelBB;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TestMapper {

    TestMapper INSTANCE = Mappers.getMapper( TestMapper.class );

    ModelBB sourceToDestination(ModelB source);
//    ModelB destinationToSource(ModelBB destination);
}