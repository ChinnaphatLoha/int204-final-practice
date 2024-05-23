package sit.int204.int204final.configs;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sit.int204.int204final.utils.ListMapper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Configuration
public class ApplicationConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        // Set matching strategy
        modelMapper.getConfiguration()
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE)
                .setMatchingStrategy(org.modelmapper.convention.MatchingStrategies.STRICT);

        // Add converter for String to LocalDate
        modelMapper.addConverter(context -> {
            String source = context.getSource();
            return source == null ? null : LocalDate.parse(source, DateTimeFormatter.ISO_LOCAL_DATE);
        }, String.class, LocalDate.class);

        return modelMapper;
    }

    @Bean
    public ListMapper listMapper() {
        return ListMapper.getInstance();
    }
}
