package org.skypro.skyshop.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skypro.skyshop.model.search.SearchResult;
import org.skypro.skyshop.model.search.Searchable;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SearchServiceTest {

    @Mock
    private StorageService storageService;

    @InjectMocks
    private SearchService searchService;

    @Test
    public void whenNoObjectsInStorageService_thenReturnEmptyCollection() {
        when(storageService.getAllSearchables()).thenReturn(List.of());

        List<SearchResult> results = (List<SearchResult>) searchService.search("pattern");

        assertThat(results)
                .isNotNull()
                .isEmpty();
    }

    @Test
    public void whenObjectsExistButNoMatch_thenReturnEmptyCollection() {
        Searchable fakeSearchable = new Searchable() {
            @Override public String getSearchTerm() { return "unrelated data"; }
            @Override public String getContentType() { return "PRODUCT"; }
            @Override public String getName() { return "Unrelated Product"; }
            @Override public UUID getId() { return UUID.randomUUID(); }
        };

        when(storageService.getAllSearchables()).thenReturn(List.of(fakeSearchable));

        List<SearchResult> results = (List<SearchResult>) searchService.search("pattern");

        assertThat(results)
                .isNotNull()
                .isEmpty();
    }

    @Test
    public void whenMatchingObjectExists_thenReturnNonEmptyCollection() {
        Searchable matchingSearchable = new Searchable() {
            @Override
            public String getSearchTerm() {
                return "irrelevant";
            }

            @Override
            public String getContentType() {
                return "ARTICLE";
            }

            @Override
            public String getName() {
                return "This contains pattern inside";
            }

            @Override
            public UUID getId() {
                return UUID.fromString("11111111-1111-1111-1111-111111111111");
            }
        };

        when(storageService.getAllSearchables()).thenReturn(List.of(matchingSearchable));

        List<SearchResult> results = (List<SearchResult>) searchService.search("pattern");

        assertThat(results)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        SearchResult result = results.get(0);
        assertThat(result.getId()).isEqualTo(matchingSearchable.getId().toString());
        assertThat(result.getName()).isEqualTo(matchingSearchable.getName());
        assertThat(result.getContentType()).isEqualTo(matchingSearchable.getContentType());
    }
}
