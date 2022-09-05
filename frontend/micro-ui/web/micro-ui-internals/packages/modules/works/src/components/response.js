import { Banner, Card, Loader, CardText, ActionBar, SubmitBar } from "@egovernments/digit-ui-react-components";
import { useQueryClient } from "react-query";
import React, { useEffect } from "react";
import { useTranslation } from "react-i18next";
import { Link } from "react-router-dom";
import { CreateEstimateIcon } from "@egovernments/digit-ui-react-components";
import { useHistory } from "react-router-dom";

// state = {
//     header,idText,id,message,links
// }

const state = {
    header:"Estimate Created and Forwarded Successfully",
    id:"EP/ENG/00001/07/2021-22",
    info:"Estimate ID",
    message:"A new Estimate has been created successfully and forwarded to Designation or the <Department>  Department for processing.",
    links:[
        {
            name:"Create new Estimate",
            redirectUrl:"/digit-ui/employee/works/create-estimate",
            code:"",
            svg:"CreateEstimateIcon"
        }
    ]

}
const Response = (props) => {
    const history = useHistory()
    const {t} = useTranslation()
    
    //get all the required data from the state while doing history.push
  return (
    <Card>
          <Banner
              message={state.header}
              applicationNumber={state.id}
            //   info={props.mutation.isSuccess ? props.surveyTitle : ""}
               info={state.info}

              //successful={props.mutation.isSuccess}
              successful={true}
              whichSvg={"tick"}
          // svg={() => <TickMark fillColor="green" />}
          />
          <CardText>
              {/* {mutation.isSuccess ?
                  // ? t(`SURVEY_FORM_CREATION_MESSAGE`, {
                  //     surveyName: survey?.title,
                  //     fromDate: Digit.DateUtils.ConvertTimestampToDate(survey?.startDate),
                  //     toDate: Digit.DateUtils.ConvertTimestampToDate(survey?.endDate),
                  //   })
                  t("SURVEY_FORM_RESPONSE_MESSAGE")
                  : null} */}
                  {t(state.message)}
          </CardText>
          <div style={{"display":"flex","justifyContent":"flex-end"}}>
                  {state.links.map(link => (
                      <div className="primary-label-btn d-grid" style={{ marginLeft: "unset", marginBottom: "10px", padding: "0px 8px" }} onClick={()=> {
                        history.push(link.redirectUrl)
                      }}>
                          <p><CreateEstimateIcon style={{ "display": "inline" }} /> {t(link.name)}</p>
                    </div>
                  ))}
          </div>
          <ActionBar>
              <Link to={"/digit-ui/employee"}>
                  <SubmitBar label={t("CORE_COMMON_GO_TO_HOME")} />
              </Link>
          </ActionBar>

    </Card>
  )
}

export default Response