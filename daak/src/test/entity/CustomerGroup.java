package test.entity;

public class CustomerGroup {
    private Integer id;
    private String title;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public enum FieldNames {
        ID("id"),
        TITLE("Title");

        private String value;

        FieldNames(String value) {
            this.value = value;
        }


        @Override
        public String toString() {
            return value.toString();
        }
    }
}
