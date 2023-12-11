package com.github.colinbatesvt.grocerylist.listener;

import com.github.colinbatesvt.grocerylist.model.GroceryList.GroceryList;
import com.github.colinbatesvt.grocerylist.service.PrimarySequenceService;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

@Component
public class GroceryListListener extends AbstractMongoEventListener<GroceryList> {

    private final PrimarySequenceService primarySequenceService;

    public GroceryListListener(final PrimarySequenceService primarySequenceService) {
        this.primarySequenceService = primarySequenceService;
    }

    @Override
    public void onBeforeConvert(final BeforeConvertEvent<GroceryList> event) {
        if (event.getSource().getId() == null) {
            event.getSource().setId(primarySequenceService.getNextValue(GroceryList.SEQUENCE_NAME));
        }
    }

}
