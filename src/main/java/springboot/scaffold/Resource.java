package springboot.scaffold;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;

import lombok.Data;

@Data
public class Resource {
    /**
     * リソースクラス名
     */
    private String resourceClass;
    /**
     * パッケージ名
     */
    private String packageName;
    /**
     * IDのデータ型
     */
    private String idType;
    /**
     * モデルプロパティ
     */
    private List<Property> property = new ArrayList<>();

    public Resource(String[] args) {
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-p":
                    setPackageName(args[++i]);
                    break;
                case "-c":
                    setProperty(args[++i]);
                    break;
            }
        }
        validate();
    }

    /**
     * リソース名を返却します。
     */
    public String getResourceName() {
        return this.resourceClass.substring(0, 1).toLowerCase() + this.resourceClass.substring(1);
    }

    private void setProperty(String classInfo) {
        try {
            int beginPos = classInfo.indexOf("[");
            int endPos = classInfo.indexOf("]");
            this.resourceClass = classInfo.substring(0, beginPos);

            Stream.of(classInfo.substring(beginPos + 1, endPos).split(",")).forEach(p -> {
                String[] arr = p.split(":");
                if ("id".equals(arr[0])) {
                    this.idType = arr[1];
                }
                property.add(new Property(arr[0], arr[1]));
            });
        } catch (Exception e) {
            throw new InvalidParameterException("クラスの指定に誤りがあります。クラス名[プロパティ名:データ型,...]");
        }
    }

    private void validate() {
        if (StringUtils.isEmpty(this.packageName)) {
            throw new InvalidParameterException("packageName is empty.");
        }
        if (StringUtils.isEmpty(this.resourceClass)) {
            throw new InvalidParameterException("resourceClass is empty.");
        }
        if (StringUtils.isEmpty(this.idType)) {
            throw new InvalidParameterException("id type is empty.");
        }
    }

    /**
     * プロパティ情報
     */
    @Data
    public static class Property {
        /**
         * データ型
         */
        private String type;

        /**
         * プロパティ名
         */
        private String name;

        public Property(String name, String type) {
            this.type = type;
            this.name = name;
        }
    }
}
