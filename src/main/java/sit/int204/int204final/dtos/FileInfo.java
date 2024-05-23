package sit.int204.int204final.dtos;

import lombok.Data;

@Data
public class FileInfo {
    private String filename;
    private long size;
    private String url;
}
