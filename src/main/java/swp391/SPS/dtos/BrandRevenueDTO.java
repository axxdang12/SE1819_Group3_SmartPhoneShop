package swp391.SPS.dtos;

public class BrandRevenueDTO {
    private String brandName;
    private Integer total;

    public BrandRevenueDTO(String brandName, Integer total) {
        this.brandName = brandName;
        this.total = total;
    }
    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
