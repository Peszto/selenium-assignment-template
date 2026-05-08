package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.ProductPage;

public class ProductTest extends BaseTest {

    private static final String PRODUCT_URL =
            "https://moobius.hu/konyv/regeny/romantikus/balo-fruzsina/ertem-szulettel";

    @Test(description = "Product page has a comment textarea with the correct placeholder")
    public void productPageHasCommentTextarea() {
        ProductPage productPage = userActions.openProductPage(PRODUCT_URL);

        productPage.switchToCommentsTab();

        Assert.assertTrue(productPage.isCommentTextareaPresent(),
                "Comment textarea should be present on the product detail page");
        Assert.assertEquals(productPage.getCommentPlaceholder(), "Hozzászólás",
                "Textarea placeholder should be 'Hozzászólás'");
    }

}
