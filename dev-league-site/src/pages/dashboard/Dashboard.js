import React from "react";
import { Row, Col, Progress, Input, Table, Label } from "reactstrap";

import Widget from "../../components/Widget";

import s from "./Dashboard.module.scss";

import config from '../../config';
import { Sparklines, SparklinesBars } from "react-sparklines";

const axios = require('axios').default;

class Dashboard extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      project: {},
      checkboxes2: [],
      graph: null,
      checkedArr: [false, false, false],
    };
    this.checkTable = this.checkTable.bind(this);
  }

  componentDidMount() {
    var parent = this;
    axios.get(config.baseUrl.api+'projects')
      .then(function (response) {
          var mainProject = response.data.projects[0];
          parent.setState({project: mainProject});
          
          axios.get(config.baseUrl.api+'dashboard/summary?projectId='+mainProject.id)
          .then(function (response) {
              parent.setState({summary: response.data});
          });
          
          axios.get(config.baseUrl.api+'dashboard/user-stories?projectId='+mainProject.id)
          .then(function (response) {
              parent.setState({userStories: response.data.userStories});
          });
          
          axios.get(config.baseUrl.api+'dashboard/sprints-velocity?projectId='+mainProject.id)
          .then(function (response) {
              parent.setState({sprintsVelocity: response.data.sprints});
          });
          
          axios.get(config.baseUrl.api+'notifications/warning?page=0')
          .then(function (response) {
              parent.setState({warnings: response.data.notifications});
          });
      });
  }

  checkTable(id) {
    let arr = [];
    if (id === 0) {
      const val = !this.state.checkedArr[0];
      for (let i = 0; i < this.state.checkedArr.length; i += 1) {
        arr[i] = val;
      }
    } else {
      arr = this.state.checkedArr;
      arr[id] = !arr[id];
    }
    if (arr[0]) {
      let count = 1;
      for (let i = 1; i < arr.length; i += 1) {
        if (arr[i]) {
          count += 1;
        }
      }
      if (count !== arr.length) {
        arr[0] = !arr[0];
      }
    }
    this.setState({
      checkedArr: arr,
    });
  }

  render() {
    return (
      <div className={s.root}>
        <h1 className="page-title">
          Dashboard &nbsp;
          <small>
            <small>{this.state.project.name}</small>
          </small>
        </h1>

        <Row>
          { this.state.summary && this.state.summary.main && this.state.summary.main.current &&  
            (
            <Col lg={6} xl={6} xs={12}>
              <Widget title={<h6> <strong>TOTAL</strong> - Current Sprint </h6>} close settings>
                <div className="stats-row">
                  <div className="stat-item">
                    <h6 className="name">Total HUs</h6>
                    <p className="value">{this.state.summary.main.current.totalHUs}</p>
                  </div>
                  <div className="stat-item">
                    <h6 className="name">In progress</h6>
                    <p className="value">{this.state.summary.main.current.workingHUs}</p>
                  </div>
                  <div className="stat-item">
                    <h6 className="name">Finished</h6>
                    <p className="value">{this.state.summary.main.current.finishedHUs}</p>
                  </div>
                </div>
                <Progress
                  color="success"
                  value={ this.state.summary.main.current.finishedHUs*100/this.state.summary.main.current.totalHUs }
                  className="bg-subtle-blue progress-xs"
                />
              </Widget>
            </Col>
          ) }
          { this.state.summary && this.state.summary.main && this.state.summary.main.previous &&  
            (
            <Col lg={6} xl={6} xs={12}>
              <Widget title={<h6> <strong>TOTAL</strong> - Previous Sprint </h6>} close settings>
                <div className="stats-row">
                  <div className="stat-item">
                    <h6 className="name">Total HUs</h6>
                    <p className="value">{this.state.summary.main.previous.totalHUs}</p>
                  </div>
                  <div className="stat-item">
                    <h6 className="name">Finished</h6>
                    <p className="value">{this.state.summary.main.previous.finishedHUs}</p>
                  </div>
                  <div className="stat-item">
                    <h6 className="name">Unfinished</h6>
                    <p className="value">{this.state.summary.main.previous.workingHUs}</p>
                  </div>
                </div>
                <Progress
                  color="success"
                  value={ this.state.summary.main.previous.finishedHUs*100/this.state.summary.main.previous.totalHUs }
                  className="bg-subtle-blue progress-xs"
                />
              </Widget>
            </Col>
          ) }

      </Row>

        { this.state.summary && this.state.summary.clients? 
          this.state.summary.clients.map((value, index) =>
          (
          <Row>
            <Col lg={6} xl={6} xs={12}>
              <Widget title={<h6> <strong>{value.name}</strong> - Current Sprint </h6>} close settings>
                <div className="stats-row">
                  <div className="stat-item">
                    <h6 className="name">Total HUs</h6>
                    <p className="value">{value.current.totalHUs}</p>
                  </div>
                  <div className="stat-item">
                    <h6 className="name">In progress</h6>
                    <p className="value">{value.current.workingHUs}</p>
                  </div>
                  <div className="stat-item">
                    <h6 className="name">Finished</h6>
                    <p className="value">{value.current.finishedHUs}</p>
                  </div>
                </div>
                <Progress
                  color="success"
                  value={ value.current.finishedHUs*100/value.current.totalHUs }
                  className="bg-subtle-blue progress-xs"
                />
              </Widget>
            </Col>
            <Col lg={6} xl={6} xs={12}>
              <Widget title={<h6> <strong>{value.name}</strong> - Previous Sprint </h6>} close settings>
                <div className="stats-row">
                  <div className="stat-item">
                    <h6 className="name">Total HUs</h6>
                    <p className="value">{value.previous.totalHUs}</p>
                  </div>
                  <div className="stat-item">
                    <h6 className="name">Finished</h6>
                    <p className="value">{value.previous.finishedHUs}</p>
                  </div>
                  <div className="stat-item">
                    <h6 className="name">Unfinished</h6>
                    <p className="value">{value.previous.workingHUs}</p>
                  </div>
                </div>
                <Progress
                  color="success"
                  value={ value.previous.finishedHUs*100/value.previous.totalHUs }
                  className="bg-subtle-blue progress-xs"
                />
              </Widget>
            </Col>
          </Row>
        )): "" }

        <Widget
              title={
                <h5>
                  <span>User stories</span>
                </h5>
              }
              settings
              close
            >
              <h3>
                Current Sprint - <span className="fw-semi-bold">User Stories</span>
              </h3>
              <p>
                Summary of the information about all user stories assigned to current sprint.
              </p>
              <Table className="table-bordered table-lg mt-lg mb-0">
                <thead className="text-uppercase">
                  <tr>
                    <th>Key</th>
                    <th className="text-right">Name</th>
                    <th className="text-right">Status</th>
                    <th className="text-right">Responsible</th>
                    <th className="text-right">Client</th>
                    <th className="text-right">Story points</th>
                    <th className="text-right">Dev hours</th>
                    <th className="text-right">QA hours</th>
                  </tr>
                </thead>
                <tbody>
                  { this.state.userStories && this.state.userStories.map((value, index) => (
                    <tr>
                      <td><a href={"https://salsa-tech.atlassian.net/browse/"+value.key}>{value.key}</a></td>
                      <td>{value.name}</td>
                      <td>{value.status}</td>
                      <td>{value.responsible}</td>
                      <td>{value.client}</td>
                      <td>{value.storyPoints}</td>
                      <td>{value.devHours.toFixed(2)}</td>
                      <td>{value.qaHours.toFixed(2)}</td>
                    </tr>
                  )) }
                </tbody>
              </Table>
            </Widget>

            <Widget
              title={
                <h5>
                  <span>Sprint Velocity</span>
                </h5>
              }
              settings
              close
            >
              <p>
                Analysis of the sprints velocity for the last sprints.
              </p>
              <Table className="table-bordered table-lg mt-lg mb-0">
                <thead className="text-uppercase">
                  <tr>
                    <th>Sprint</th>
                    <th className="text-right">Team Members</th>
                    <th className="text-right">Release Speed</th>
                    <th className="text-right">Average Speed</th>
                    <th className="text-right">Standard Deviation</th>
                    <th className="text-right">Minimum Speed</th>
                    <th className="text-right">Maximum Speed</th>
                  </tr>
                </thead>
                <tbody>
                  { this.state.sprintsVelocity && this.state.sprintsVelocity.map((value, index) => (
                    <tr>
                      <td>{value.title}</td>
                      <td>{value.teamMembers}</td>
                      <td>{value.releaseSpeed}</td>
                      <td>{value.avgSpeed? value.avgSpeed.toFixed(2): 0.00}</td>
                      <td>{value.stdevSpeed? value.stdevSpeed.toFixed(2): 0.00}</td>
                      <td>{value.minSpeed? value.minSpeed.toFixed(2): 0.00}</td>
                      <td>{value.maxSpeed? value.maxSpeed.toFixed(2): 0.00}</td>
                    </tr>
                  )) }
                </tbody>
                <thead className="text-uppercase">
                  <tr>
                    <th>Sprint</th>
                    <th className="text-right">Team Members</th>
                    <th className="text-right">Release Speed</th>
                    <th className="text-right">Average Speed</th>
                    <th className="text-right">Standard Deviation</th>
                    <th className="text-right">Minimum Speed</th>
                    <th className="text-right">Maximum Speed</th>
                  </tr>
                </thead>
              </Table>
            </Widget>

        <Row>
          <Col lg={4} xs={12}>
            <Widget
              title={
                <h6>
                  Information
                </h6>
              }
              refresh
              close
            >
              <div className="widget-body undo_padding">
                <div className="list-group list-group-lg">
                  { this.state.warnings && 
                  this.state.warnings.map((value, index) => 
                    (
                      <button className="list-group-item text-left">
                        <div>
                          <p className="m-0">{value.text}</p>
                          <p className="help-block text-ellipsis m-0">{value.createdDate}</p>
                        </div>
                      </button>
                    )) }
                </div>
              </div>
            </Widget>
          </Col>
          <Col lg={4} xs={12}>
            <Widget
              title={
                <h6>
                  Warnings
                </h6>
              }
              refresh
              close
            >
              <div className="widget-body undo_padding">
                <div className="list-group list-group-lg">
                  { this.state.warnings && 
                  this.state.warnings.map((value, index) => 
                    (
                      <button className="list-group-item text-left">
                        <div>
                          <p className="m-0">{value.text}</p>
                          <p className="help-block text-ellipsis m-0">{value.createdDate}</p>
                        </div>
                      </button>
                    )) }
                </div>
              </div>
            </Widget>
          </Col>
          <Col lg={4} xs={12}>
            <Widget
              title={
                <h6>
                  Errors
                </h6>
              }
              refresh
              close
            >
              <div className="widget-body undo_padding">
                <div className="list-group list-group-lg">
                  { this.state.warnings && 
                  this.state.warnings.map((value, index) => 
                    (
                      <button className="list-group-item text-left">
                        <div>
                          <p className="m-0">{value.text}</p>
                          <p className="help-block text-ellipsis m-0">{value.createdDate}</p>
                        </div>
                      </button>
                    )) }
                </div>
              </div>
            </Widget>
          </Col>

        </Row>
      </div>
    );
  }
}

export default Dashboard;
