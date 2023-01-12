import { Loader } from "@egovernments/digit-ui-react-components";
import React, { useEffect } from "react";
import { useRouteMatch } from "react-router-dom";
import MastersCard from "./components/MastersCard";
import ProjectCard from "./components/ProjectCard";
import { default as EmployeeApp } from "./pages/employee";
import SearchOrganization from "./pages/employee/Master/SearchOrganization";
import CreateOrganization from "./pages/employee/Master/CreateOrganization";
import RegisterWageSeeker from "./pages/employee/registerWageSeeker/index";
import SearchOrganisationApplication from "./components/SearchOrganisation";
import ViewOrganisation from "./pages/employee/Master/ViewOrganisation";

export const MastersModule = ({ stateCode, userType, tenants }) => {
  const moduleCode = ["Masters"];
  const { path, url } = useRouteMatch();
  const tenantId = Digit.ULBService.getCurrentTenantId();
  const language = Digit.StoreData.getCurrentLanguage();
  const { isLoading, data: store } = Digit.Services.useStore({
    stateCode,
    moduleCode,
    language,
  });

  useEffect(() => {
    Digit.LocalizationService.getLocale({
      modules: [`rainmaker-${tenantId}`],
      locale: language,
      tenantId: stateCode,
    })
  }, [])

  if (isLoading) {
    return <Loader />;
  }
  return <EmployeeApp path={path} stateCode={stateCode} userType={userType} tenants={tenants} />;
};

const componentsToRegister = {
  MastersModule,
  MasterCard:MastersCard ,
  ProjectsCard:ProjectCard,
  SearchOrganization,
  CreateOrganization,
  RegisterWageSeeker,
  SearchOrganisationApplication,
  ViewOrganisation
};

export const initMastersComponents = () => {
  Object.entries(componentsToRegister).forEach(([key, value]) => {
    Digit.ComponentRegistryService.setComponent(key, value);
  });
};
