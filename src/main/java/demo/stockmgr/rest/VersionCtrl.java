package demo.stockmgr.rest;

import demo.stockmgr.config.AppConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Stream;

/**
 * Version Controller
 * @since Teki Chan
 * @since 28 May 2019
 */
@RestController
@PropertySource("classpath:configVersion.properties")
public class VersionCtrl {
    /** URL for Version Information */
    private static final String URL_VERSION = AppConfig.REST_PREFIX + "/version";
    /** Response Field for Version Information */
    private static final String FIELD_VERSION = "version_info";

    @Value("${version.system_name}")
    private String systemName;

    @Value("${version.dtap}")
    private String dtap;

    @Value("${version.build_timestamp}")
    private String buildTimestamp;

    @Value("${version.numbering}")
    private String versionNumbering;

    /**
     * Restful Endpoint to describe Version
     * @return  Restful Endpoint
     */
    @GetMapping(
            value=URL_VERSION
            , produces={MediaType.APPLICATION_JSON_VALUE}
    )
    @ResponseBody
    public Map<String, Object> getVersionJson() {
        return this.getVersionInfo();
    }

    /**
     * Construct Version Information into Map<String, Object> object
     * @return  Version Information in Map
     */
    private Map<String, Object> getVersionInfo() {
        return Collections.unmodifiableMap(Stream.of(
                new AbstractMap.SimpleEntry<>(FIELD_VERSION, Collections.unmodifiableMap(Stream.of(
                        new AbstractMap.SimpleEntry<>("system_name", systemName),
                        new AbstractMap.SimpleEntry<>("version_number", versionNumbering),
                        new AbstractMap.SimpleEntry<>("build_timestamp", buildTimestamp),
                        new AbstractMap.SimpleEntry<>("dtap", dtap),
                        new AbstractMap.SimpleEntry<>("timestamp", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()))
                ).collect(HashMap::new, (objMap, objEntity) -> objMap.put(objEntity.getKey(), objEntity.getValue()), HashMap::putAll)))
        ).collect(HashMap::new, (objMap, objEntity) -> objMap.put(objEntity.getKey(), objEntity.getValue()), HashMap::putAll));
    }
}
