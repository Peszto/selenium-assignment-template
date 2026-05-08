package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.BooksPage;

import java.util.List;

public class BooksTest extends BaseTest {

    @Test(description = "Books page displays at least one product")
    public void booksPageDisplaysAtLeastOneProduct() {
        BooksPage booksPage = userActions.openBooksPage();

        Assert.assertTrue(booksPage.getProductCount() > 0,
                "Books page should display at least one product card");
    }

    @Test(description = "Books page title contains 'Moobius'")
    public void booksPageTitleContainsMoobius() {
        BooksPage booksPage = userActions.openBooksPage();

        Assert.assertTrue(booksPage.getPageTitle().contains("Moobius"),
                "Books page title should contain 'Moobius', but was: " + booksPage.getPageTitle());
    }

    @Test(description = "Sort dropdown lists all expected sort options")
    public void sortDropdownHasExpectedOptions() {
        BooksPage booksPage = userActions.openBooksPage();

        List<String> options = booksPage.getSortOptions();

        Assert.assertTrue(options.contains("Legújabb elöl"),
                "Sort dropdown should contain 'Legújabb elöl'");
        Assert.assertTrue(options.contains("Cím szerint A-Z"),
                "Sort dropdown should contain 'Cím szerint A-Z'");
        Assert.assertTrue(options.size() >= 8,
                "Sort dropdown should have at least 8 options, but had: " + options.size());
    }

    @Test(description = "Selecting a sort option via the Select class changes the active selection")
    public void selectingSortOptionChangesSelection() {
        BooksPage booksPage = userActions.openBooksPage();

        booksPage.sortBy("Cím szerint A-Z");

        Assert.assertEquals(booksPage.getSelectedSortOption(), "Cím szerint A-Z",
                "The selected sort option should reflect the choice made via the Select class");
    }
}
