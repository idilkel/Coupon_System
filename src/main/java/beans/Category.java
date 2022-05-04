package beans;

public enum Category {
    RESTAURANTS,
    TRAVEL,
    ENTERTAINMENT,
    FASHION,
    ELECTRONICS;

    public static Category getCategoryFromId(int categoryId) {
        return values()[categoryId - 1];
    }

    
}
