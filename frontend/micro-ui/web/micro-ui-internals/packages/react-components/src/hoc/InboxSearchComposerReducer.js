export const initialInboxState = {
    searchForm:{

    },
    filterForm:{

    },
    tableForm:{

    } 
};

const reducer = (state, action) => {
    switch (action.type) {
        case "searchForm":
            console.log('action SEARCH', action);
            const {state:updatedSearchState} = action
            Object.keys(updatedSearchState).forEach(key => {
                if(!updatedSearchState[key])  delete updatedSearchState[key] 
            }); 
            return {...state, searchForm: updatedSearchState};
        case "filterForm":
            return state;
        case "tableForm":
            const updatedTableState = action.state
            return { ...state, tableForm: updatedTableState };
        case "clearSearchForm":
            return {...state,searchForm:action.state}
        default:
            return state;
    }
}

export default reducer;
