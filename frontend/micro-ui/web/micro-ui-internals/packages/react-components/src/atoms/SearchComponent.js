import React, { useContext } from "react";
import { useForm } from "react-hook-form";
import { useTranslation } from "react-i18next";
import Header from "../atoms/Header";
import RenderFormFields from "../molecules/RenderFormFields";
import LinkLabel from '../atoms/LinkLabel';
import SubmitBar from "../atoms/SubmitBar";
import { InboxContext } from "../hoc/InboxSearchComposerContext";

const SearchComponent = ({ uiConfig, header = "", screenType = "search"}) => {
  const { t } = useTranslation();
  const { state, dispatch } = useContext(InboxContext)

  const {
    register,
    handleSubmit,
    setValue,
    getValues,
    reset,
    watch,
    trigger,
    control,
    formState,
    errors,
    setError,
    clearErrors,
    unregister,
  } = useForm({
    defaultValues: uiConfig?.defaultValues,
  });

  const formData = watch();

  const checkKeyDown = (e) => {
    const keyCode = e.keyCode ? e.keyCode : e.key ? e.key : e.which;
    if (keyCode === 13) {
      e.preventDefault();
    }
  };

  const onSubmit = (data) => {
    //send data to reducer
    dispatch({
      type: "searchForm",
      state: {
        data: data,
        searchClicked: true
      }
    })
    
  }

  const clearSearch = () => {
    reset(uiConfig?.defaultValues)
  }
 
  return (
    <React.Fragment>
      <div className={'search-wrapper'}>
        {header && <Header styles={uiConfig?.headerStyle}>{header}</Header>}
        <form onSubmit={handleSubmit(onSubmit)} onKeyDown={(e) => checkKeyDown(e)}>
          <div className={`search-field-wrapper ${screenType} ${uiConfig?.type}`}>
            <RenderFormFields 
              fields={uiConfig?.fields} 
              control={control} 
              formData={formData}
              errors={errors}
              register={register}
              setValue={setValue}
              getValues={getValues}
              setError={setError}
              clearErrors={clearErrors}
              labelStyle={{fontSize: "16px"}}
            />  
            <div className={`search-button-wrapper ${screenType}`}>
              <LinkLabel style={{marginBottom: 0, whiteSpace: 'nowrap'}} onClick={clearSearch}>{uiConfig?.secondaryLabel}</LinkLabel>
              <SubmitBar label={uiConfig?.primaryLabel} submit="submit" disabled={false}/>
            </div>
          </div> 
        </form>
      </div>
    </React.Fragment>
  )
}

export default SearchComponent