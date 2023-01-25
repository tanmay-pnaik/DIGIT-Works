const inboxConfig = (t) => {
    return {
        label : "Inbox",
        type : "inbox", 
        //use classname as 'search' for search view
        //use classname as 'inbox' for inbox view
        //inbox view is by default
        sections : {
            search : {
                uiConfig : {
                    headerStyle : null,
                    buttonLabel: 'Search',
                    linkLabel: 'Clear Search',
                    defaultValues : {
                        projectId: "",
                        department: "",
                        workType: ""
                    },
                    fields : [
                        {
                            label:"Project ID",
                            type: "text",
                            isMandatory: false,
                            disable: false,
                            populators: { 
                                name: "projectId"
                            },
                        },
                        {
                            label: "Department",
                            type: "dropdown",
                            isMandatory: false,
                            disable: false,
                            populators: {
                              name: "department",
                              optionsKey: "name",
                              mdmsConfig: {
                                masterName: "Department",
                                moduleName: "common-masters",
                                localePrefix: "WORKS",
                              }
                            }
                        },
                        {
                          label: "Type Of Work",
                          type: "dropdown",
                          isMandatory: false,
                          disable: false,
                          populators: {
                            name: "workType",
                            optionsKey: "name",
                            mdmsConfig: {
                              masterName: "TypeOfWork",
                              moduleName: "works",
                              localePrefix: "WORKS",
                            }
                          }
                        }
                    ]
                },
                label : "",
                children : {},
                show : true
            },
            links : {
                uiConfig : {
                    links : [
                        {
                            label: "ACTION_TEST_PROJECTS",
                            link: `/employee/contracts/create-contract`,
                            businessService: "WORKS",
                        }
                    ],            
                },
                label : "Projects",
                children : {},
                show : true //by default true. 
            },
            filter : {
                label : "",
                uiConfig : {
                    
                },
                children : {},
                show : true //by default true. 
            },
            searchResult : {
                label : "",
                uiConfig: {
                    defaultValues: {
                        offset: 0,
                        limit: 10,
                        // sortBy: "department",
                        sortOrder: "ASC",
                    },
                    columns: [
                        {
                            label: "name",
                            jsonPath: "searchResult[0].name",
                            accessor: "name",
                            isLink: true,
                            redirectUrl: '/works-ui/employee/project/project-inbox-item'
                        },
                        {
                            label: "age",
                            jsonPath: "searchResult[0].age",
                            accessor: "age",
                            isLink: false,
                            redirectUrl: ""
                        },
                        {
                            label: "gender",
                            jsonPath: "searchResult[0].age",
                            accessor: "gender",
                            isLink: false,
                            redirectUrl: ""
                        },
                        {
                            label: "company",
                            jsonPath: "searchResult[0].company",
                            accessor: "company",
                            isLink: false,
                            redirectUrl: ""
                        },
                        {
                            label: "email",
                            jsonPath: "searchResult[0].email",
                            accessor: "email",
                            isLink: false,
                            redirectUrl: ""
                        },
                        {
                            label: "phone",
                            jsonPath: "searchResult[0].phone",
                            accessor: "phone",
                            isLink: false,
                            redirectUrl: ""
                        },
                        {
                            label: "balance",
                            jsonPath: "searchResult[0].balance",
                            accessor: "balance",
                            isLink: false,
                            redirectUrl: ""
                        },
                        {
                            label: "favoriteFruit",
                            jsonPath: "searchResult[0].favoriteFruit",
                            accessor: "favoriteFruit",
                            isLink: false,
                            redirectUrl: ""
                        },
                        {
                            label: "eyeColor",
                            jsonPath: "searchResult[0].eyeColor",
                            accessor: "eyeColor",
                            isLink: false,
                            redirectUrl: ""
                        }
                    ],
                    enableGlobalSearch: false,
                    enableColumnSort: true,

                },
                children : {},
                show : true //by default true. 
            }
        },
        additionalSections : {
            //Open for Extensions
            //One can create a diff Parent card and add additional fields in it.
        }
    }
}

export default inboxConfig;