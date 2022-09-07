export const newConfig = [
    {
      head: "WORKS_APP_FOR_CREATE_CONTRACTOR",
      key: "ConnectionHolderDetails",
      isCreate: true,
      hideInCitizen: true,
      body: [
      {
        head: "WORKS_CONTRACTOR_DETAILS",
        // isEditConnection: true,
        isCreateContracte: true,
        // isModifyConnection: true,
        // isEditByConfigConnection: true,
        body: [{
          type: "component",
          key: "ContractorDetails",
          component: "WORKSContractorDetails",
          withoutLabel: true
        }]
      },
      {
        head: "WORKS_CONTRACTOR_TABLE",
        // isEditConnection: true,
        isCreateContracte: true,
        // isModifyConnection: true,
        // isEditByConfigConnection: true,
        body: [{
          type: "component",
          key: "ContractorTable",
          component: "WORKSContractorTable",
          withoutLabel: true
        }]
      }
      ]
    }
  ]