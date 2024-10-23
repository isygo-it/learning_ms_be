package eu.novobit.dto.data;

import eu.novobit.dto.AbstractAuditableDto;
import eu.novobit.dto.IImageUploadDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


    /**
     * The type Topic dto.
     */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @SuperBuilder
    public class TopicDto extends AbstractAuditableDto<Long> implements
            IImageUploadDto{
        private Long id;
        private String code;
        private String name;
        private String domain;
        private String description;
        private String imagePath;
        // Constructor with id, name, and imagePath for the JPQL query
        public TopicDto(Long id, String name, String imagePath) {
            this.id = id;
            this.name = name;
            this.imagePath = imagePath;
        }
    }
