import React, {Component} from "react";
import {Card, Upload,Radio,Form,Icon, Checkbox,InputNumber,Input, Modal, Select, Row, Col, Button,DatePicker} from "antd";
import LoanListAction from "actions/LoanListAction";
import { propsToFields,isEmptyObject } from 'services/functions';
import PicturesWall from '../common/PicturesWall'
import Moment from 'moment'
const RadioGroup = Radio.Group;
const CheckboxGroup = Checkbox.Group;
const FormItem = Form.Item;
const Option = Select.Option;
const show = (info) => {
  console.log("jsx loanListAddModal: " + JSON.stringify(info));
}
const planFinancingTypeOptions = [
  { label: '商业险', value: 'A' },
  { label: '交强险', value: 'B' },
  { label: '车船使用税', value: 'C' },
];
class LoanListAddModal extends Component {


  handleAddCancel =(e)=> {
    LoanListAction.openAddModal();
  };
  handleOnSubmit = (e) => {
    this.props.form.validateFieldsAndScroll((err, values) => {
      console.log("handleOnSubmit:"+JSON.stringify(values))
          if (!err) {
            LoanListAction.submitLoan(values);
      }
    });
  }
  handleOnSave = (e) => {
    this.props.form.validateFieldsAndScroll((err, values) => {
      console.log("handleOnSave:"+JSON.stringify(values))
      if (!err) {
        if(!isEmptyObject(this.props.loanInfo)){
          //修改
          LoanListAction.updateLoan(values);
        }else {
          LoanListAction.addLoan(values);
        }
      }
    });
  }

  handleUploadChange = ({ fileList }) =>
  {
   show(fileList);
  }



  checkIdCardLength = (rule, value, callback) => {
    let regex = /(^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$)|(^[1-9]\d{5}\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{2}[0-9Xx]$)/;
    if (!regex.test(value)) {
      callback("请输入正确身份证号");
    }
    else {
      callback();
    }
  };
  checkMobileLength = (rule, value, callback) => {
    let regex = /^((\+)?86|((\+)?86)?)0?1[34578]\d{9}$/;
    if (!regex.test(value)) {
      callback("请输入正确手机号");
    }
    else {
      callback();
    }
  };



  render() {

   // show(this.props.loanInfo.fssLoanDocumentModels)

    const picturesWallprops={
      length:1,
      maxFileSize:2,
      // initialValue:[{
      //   uid: -1,
      //   name: 'xxx.png',
      //   status: 'done',
      //   url: 'https://zos.alipayobjects.com/rmsportal/jkjgkEfvpUPVyRjUImniVslZfWPnJuuZ.png',
      // }],
      onChange:this.handleUploadChange
    };

  const pictureWallValue=[{
    uid: -11,
    name: 'xxx.png',
    status: 'done',
    url: 'https://zos.alipayobjects.com/rmsportal/jkjgkEfvpUPVyRjUImniVslZfWPnJuuZ.png',
  }];

    const {getFieldDecorator} = this.props.form;
    const formItemLayout = {
      labelCol:{span:6,offset: 0},
      wrapperCol: {span: 18}
    };
    const formItemLayoutOneLine = {
      labelCol:{span:5,offset: -1},
      wrapperCol: {span: 19}
    };
    const formItemLayoutNoLable = {
      wrapperCol: {span: 19}
    };

    const rowLayout = {
      marginTop: 5,
      marginBottom: 5
    }
    const labelLayout = {
      span: 2
    }
    const contentLayout = {
      span: 5
    }
    return (
      <div>
        <Modal width={1000} visible={this.props.addModalVisible} title="保险分期" onCancel={this.handleAddCancel}
               footer={[
                 <Button key="back" type="ghost" size="large" onClick={this.handleAddCancel}>取消</Button>,
                 <Button key="submit" type="primary" size="large" onClick={this.handleOnSave}>保存</Button>,
                 <Button key="submitTJ" type="primary" size="large" onClick={this.handleOnSubmit}>提交审核</Button>
               ]}>
              <Form layout="horizontal">
                <Card title="被保险人信息" style={{marginBottom: 24}} bordered={true}>
                    <Row style={rowLayout}>
                      <Col span="8">
                        <FormItem label="被保人姓名" {...formItemLayout}>
                          {getFieldDecorator('insuresName', { rules: [ {required: true, message: '请输入被保险人姓名'}]} )(
                            <Input/>
                          )}
                        </FormItem>
                      </Col>
                      <Col span="8" >
                        <FormItem label="联系电话" {...formItemLayout}>
                          {getFieldDecorator('insuresPhone', { rules: [ {required: true, message: '请输入联系电话'}, {validator: this.checkMobileLength}]} )(
                            <Input/>
                          )}
                        </FormItem>
                      </Col>
                      <Col span="8" >
                        <FormItem label="身份证号" {...formItemLayout}>
                          {getFieldDecorator('insuresIdcard', { rules: [ {required: true, message: '请输入身份证号'}, {validator: this.checkIdCardLength}]})(
                            <Input/>
                          )}
                        </FormItem>
                          </Col>
                    </Row>
                  <Row style={rowLayout}>
                    <Col span="8">
                        <FormItem label="联系人姓名" {...formItemLayout}>
                          {getFieldDecorator('insuresLinkName', { rules: [ {required: true, message: '请输入联系人姓名'}]})(
                            <Input/>
                          )}
                        </FormItem>
                    </Col>
                      <Col span="8">
                        <FormItem   label="联系电话" {...formItemLayout}>
                          {getFieldDecorator('insuresLinkPhone', { rules: [ {required: true, message: '请输入联系电话'}, {validator: this.checkMobileLength}]})(
                            <Input/>
                          )}
                        </FormItem>
                      </Col>
                        <Col span="8">
                        <FormItem  label="客户类型" {...formItemLayout}>
                          {getFieldDecorator('insuresType')(
                            <Select initialValue="personal">
                              <Option value="personal">个人</Option>
                              <Option value="enterprise">企业</Option>
                            </Select>
                          )}
                        </FormItem>
                      </Col>
                  </Row>
                  <Row style={rowLayout}>
                    <Col span="10">
                    <FormItem label="被保人地址" {...formItemLayoutOneLine}>
                      {getFieldDecorator('insuresAddress')(
                        <Input/>
                      )}
                    </FormItem>
                    </Col>
                  </Row>
                  </Card>
                <Card title="车辆信息" style={{marginBottom: 24}} bordered={true}>
                  <Row style={rowLayout}>
                    <Col span="8">
                      <FormItem label="品牌车型" {...formItemLayout}>
                        {getFieldDecorator('carBrand', { rules: [ {required: true, message: '请输入品牌'}]} )(
                          <Input/>
                        )}
                      </FormItem>
                    </Col>
                    <Col span="4">
                      <FormItem style={{paddingLeft:2}}>
                        {getFieldDecorator('carVehicleName', { rules: [ {required: true, message: '请输入车系'}]} )(
                          <Input/>
                        )}
                      </FormItem>

                    </Col>
                    <Col span="4">
                      <FormItem style={{paddingLeft:2}}>
                        {getFieldDecorator('carModelName', { rules: [ {required: true, message: '请输入车型'}]} )(
                          <Input/>
                        )}
                      </FormItem>

                    </Col>
                    <Col span="8">
                      <FormItem label="车牌号" {...formItemLayout}>
                        {getFieldDecorator('carPlateNumber', { rules: [ {required: true, message: '请输入车牌号'}]} )(
                          <Input/>
                        )}
                      </FormItem>
                    </Col>

                  </Row>
                  <Row style={rowLayout}>
                    <Col span="8">
                      <FormItem label="车架号" {...formItemLayout}>
                        {getFieldDecorator('carVin', { rules: [ {required: true, message: '请输入车架号'}]})(
                          <Input/>
                        )}
                      </FormItem>
                    </Col>
                    <Col span="8">
                      <FormItem  label="发动机号" {...formItemLayout}>
                        {getFieldDecorator('carEngineNo', { rules: [ {required: true, message: '请输入发动机号'}]})(
                          <Input/>
                        )}
                      </FormItem>
                    </Col>
                    <Col span="8">
                      <FormItem  label="车辆类型" {...formItemLayout}>
                        {getFieldDecorator('carType')(
                          <Select initialValue="1">
                            <Option value="1">新车</Option>
                            <Option value="2">二手车</Option>
                          </Select>
                        )}
                      </FormItem>
                    </Col>
                  </Row>
                  <Row style={rowLayout}>
                    <Col span="8">
                      <FormItem label="购买方式" {...formItemLayout}>
                        {getFieldDecorator('carBuyType')(
                          <Select initialValue="1">
                            <Option value="1">分期</Option>
                            <Option value="2">全款</Option>
                          </Select>
                        )}
                      </FormItem>
                    </Col>
                    <Col span="8">
                      <FormItem  label="车辆颜色"  {...formItemLayout}>
                        {getFieldDecorator('carColor')(
                        <Select>
                          <Option value="1">白色</Option>
                          <Option value="2">黑色</Option>
                        </Select>
                        )}
                      </FormItem>
                    </Col>
                    <Col span="8">
                      <FormItem   label="购车日期" {...formItemLayout}>
                        {getFieldDecorator('carBuyDate')(
                          <DatePicker/>
                        )}
                      </FormItem>
                    </Col>
                  </Row>
                  <Row style={rowLayout}>
                    <Col span="8">
                      <FormItem label="座位数"  {...formItemLayout}>
                        {getFieldDecorator('carSeatNumber')(
                          <Select>
                            <Option value="5">5座</Option>
                            <Option value="7">7座</Option>
                          </Select>
                        )}
                      </FormItem>
                    </Col>
                    <Col span="8">
                      <FormItem  label="排量" {...formItemLayout}>
                        {getFieldDecorator('carDisplacement')(
                          <Input/>
                        )}
                      </FormItem>
                    </Col>
                    <Col span="8">
                      <FormItem  label="车"  {...formItemLayout}>
                        {getFieldDecorator('carImports')(
                          <RadioGroup initialValue="1">
                            <Radio value="1">国产</Radio>
                            <Radio value="2">进口</Radio>
                          </RadioGroup>
                        )}
                      </FormItem>
                    </Col>
                  </Row>
                </Card>
                <Card title="保单信息" style={{marginBottom: 24}} bordered={true}>
                  <Row style={rowLayout}>
                    <Col span="8">
                      <FormItem label="投保类型"  {...formItemLayout}>
                        {getFieldDecorator('policyType')(
                          <Select>
                            <Option value="first">首保</Option>
                            <Option value="other">非首保</Option>
                          </Select>
                        )}
                      </FormItem>
                    </Col>
                    <Col span="8">
                      <FormItem label="保险公司" {...formItemLayout}>
                        {getFieldDecorator('policyInsuranceCompany')(
                          <Input/>
                        )}
                      </FormItem>
                    </Col>
                    <Col span="8">
                      <FormItem label="保险总额" {...formItemLayout}>
                        {getFieldDecorator('policyTotalAmount')(
                          <Input/>
                        )}
                      </FormItem>
                    </Col>
                  </Row>
                  <Row style={rowLayout}>
                    <Col span="8">
                      <FormItem label="生效日期"  {...formItemLayout}>
                        {getFieldDecorator('policyEffectDate', { rules: [ {required: true, message: '请输入生效日期'}]})(
                          <DatePicker/>
                        )}
                      </FormItem>
                    </Col>
                    <Col span="8">
                      <FormItem  label="到期日期"  {...formItemLayout}>
                        {getFieldDecorator('policyExpireDate', { rules: [ {required: true, message: '请输入到期日期'}]})(
                          <DatePicker/>
                        )}
                      </FormItem>
                    </Col>
                  </Row>
                  <Row style={rowLayout}>
                  <Col span="8">
                    <FormItem label="交强险"  {...formItemLayout}>
                      {getFieldDecorator('policyCompulsoryInsurance')(
                        <InputNumber />
                      )}
                    </FormItem>
                  </Col>
                  <Col span="8">
                    <FormItem  label="车船使用税"  {...formItemLayout}>
                      {getFieldDecorator('policyVehicleTax')(
                        <InputNumber/>
                      )}
                    </FormItem>
                  </Col>
                </Row>
                  <Row style={rowLayout}>
                    <Col span="3"><Checkbox>第三者责任险</Checkbox></Col>
                    <Col span="2">
                      <FormItem  {...formItemLayout}>
                        {getFieldDecorator('policyThreePayLevel')(
                          <Select >
                            <Option value="">请选择赔付额度</Option>
                            <Option value="10">10万</Option>
                            <Option value="30">30万</Option>
                            <Option value="50">50万</Option>
                            <Option value="100">100万</Option>
                          </Select>
                        )}
                      </FormItem>
                    </Col>
                    <Col span="4">
                      <FormItem  {...formItemLayout}>
                        {getFieldDecorator('policyThreePay')(
                          <InputNumber />
                        )}
                      </FormItem>
                    </Col>
                    <Col span="3"><Checkbox>车辆损失险</Checkbox></Col>
                    <Col span="8">
                      <FormItem   {...formItemLayout}>
                        {getFieldDecorator('policyDamage')(
                          <InputNumber/>
                        )}
                      </FormItem>
                    </Col>
                  </Row>
                  <Row style={rowLayout}>
                    <Col span="3"><Checkbox>全车盗抢险</Checkbox></Col>
                    <Col span="6">
                      <FormItem  {...formItemLayout}>
                        {getFieldDecorator('policyLost')(
                          <InputNumber />
                        )}
                      </FormItem>
                    </Col>
                    <Col span="3"><Checkbox>玻璃单独破碎险</Checkbox></Col>
                    <Col span="8">
                      <FormItem   {...formItemLayout}>
                        {getFieldDecorator('policyGlassBreakage')(
                          <InputNumber/>
                        )}
                      </FormItem>
                    </Col>
                  </Row>
                  <Row style={rowLayout}>
                    <Col span="3"><Checkbox>自燃损失险</Checkbox></Col>
                    <Col span="6">
                      <FormItem  {...formItemLayout}>
                        {getFieldDecorator('policyAutoIgnition')(
                          <InputNumber />
                        )}
                      </FormItem>
                    </Col>
                    <Col span="3"><Checkbox>不计免赔特约险</Checkbox></Col>
                    <Col span="8">
                      <FormItem   {...formItemLayout}>
                        {getFieldDecorator('policyDisregardContributing') (
                          <InputNumber/>
                        )}
                      </FormItem>
                    </Col>
                  </Row>

                  <Row style={rowLayout}>
                    <Col span="3"><Checkbox>乘客座位责任险</Checkbox></Col>
                    <Col span="6">
                      <FormItem  {...formItemLayout}>
                        {getFieldDecorator('policySeatPassenger')(
                          <InputNumber />
                        )}
                      </FormItem>
                    </Col>
                    <Col span="3"><Checkbox>司机座位责任险</Checkbox></Col>
                    <Col span="8">
                      <FormItem   {...formItemLayout}>
                        {getFieldDecorator('policySeatDriver')(
                          <InputNumber/>
                        )}
                      </FormItem>
                    </Col>
                  </Row>
                  <Row style={rowLayout}>
                    <Col span="3"><Checkbox>车身划痕险</Checkbox></Col>
                    <Col span="6">
                      <FormItem  {...formItemLayout}>
                        {getFieldDecorator('policyScratch')(
                          <InputNumber />
                        )}
                      </FormItem>
                    </Col>
                    <Col span="3"><Checkbox>涉水险／发动机特别损失险</Checkbox></Col>
                    <Col span="8">
                      <FormItem   {...formItemLayout}>
                        {getFieldDecorator('policyWading')(
                          <InputNumber/>
                        )}
                      </FormItem>
                    </Col>
                  </Row>
                </Card>
                <Card title="金融方案" style={{marginBottom: 24}} bordered={true}>
                  <Row style={rowLayout}>
                    <Col span="8">
                      <FormItem label="产品名称"  {...formItemLayout}>
                        {getFieldDecorator('planId' )(
                          <Select>
                            <Option value="100">默认金融方案</Option>
                            {/*<Option value="200">12期</Option>*/}
                          </Select>
                        )}
                      </FormItem>
                    </Col>
                    <Col span="8">
                      <FormItem label="贷款期限" {...formItemLayout}>
                        {getFieldDecorator('planPeriod', { rules: [ {required: true, message: '请选择贷款期限'}]} )(
                          <Select initialValue="12">
                            <Option value="6">6期</Option>
                            <Option value="12">12期</Option>
                          </Select>
                        )}
                      </FormItem>
                    </Col>
                  </Row>
                  <Row style={rowLayout}>
                    <Col span="8">
                      <FormItem label="融资类目"  {...formItemLayout}>
                        {getFieldDecorator('planFinancingType')(
                          <CheckboxGroup options={planFinancingTypeOptions} initialValue={['A','C']}/>

                          )}
                      </FormItem>
                    </Col>
                    <Col span="8">
                      <FormItem  label="贷款总额"  {...formItemLayout}>
                        {getFieldDecorator('planLoanAmount')(
                          <InputNumber/>
                        )}
                      </FormItem>
                    </Col>
                  </Row>
                </Card>
                <Card title="还款卡信息" style={{marginBottom: 24}} bordered={true}>
                  <Row style={rowLayout}>
                    <Col span="8">
                      <FormItem label="开户行" {...formItemLayout}>
                        {getFieldDecorator('repayment_bank_code')(
                          <Select/>
                        )}
                      </FormItem>
                    </Col>
                    <Col span="8">
                      <FormItem label="还款卡号" {...formItemLayout}>
                        {getFieldDecorator('repaymentCard' )(
                          <Input/>
                        )}
                      </FormItem>
                    </Col>
                    <Col span="8">
                      <FormItem  label="手机号码" {...formItemLayout}>
                        {getFieldDecorator('insuresPhone')(
                          <Input/>
                        )}
                      </FormItem>
                    </Col>
                  </Row>
                </Card>
                <Card title="影象信息" style={{marginBottom: 24}} bordered={true}>
                  <Row style={rowLayout}>
                    <Col span="12">
                      <FormItem label="身份证正面" {...formItemLayout}>
                      {getFieldDecorator('idcardFrontPic',{initialValue:pictureWallValue})(
                          <PicturesWall  {...picturesWallprops}/>
                      )}
                      </FormItem>
                    </Col>
                    <Col span="12">
                      <FormItem label="身份证反面" {...formItemLayout}>
                      {getFieldDecorator('idcardBackPic',{initialValue:pictureWallValue})(
                        <PicturesWall  {...picturesWallprops}/>
                      )}</FormItem>
                    </Col>
                  </Row>
                    <Row style={rowLayout}>
                    <Col span="12">
                      <FormItem label="交强险保单"{...formItemLayout}>
                      {getFieldDecorator('compulsoryInsurancePic',{initialValue:pictureWallValue})(
                        <PicturesWall  {...picturesWallprops}/>
                      )}</FormItem>
                    </Col>
                      <Col  span="12">
                        <FormItem label="商业险保单" {...formItemLayout}>
                        {getFieldDecorator('commercialInsurancePic',{initialValue:pictureWallValue})(
                          <PicturesWall  {...picturesWallprops}/>
                        )}
                      </FormItem>
                      </Col>
                  </Row>
                </Card>
              </Form>
        </Modal>
      </div>)
  }

}

LoanListAddModal = Form.create({
  mapPropsToFields(props){
    show(props)
    show(isEmptyObject(props.loanInfo))
    if(!isEmptyObject(props.loanInfo))
    {
      let loan = props.loanInfo.fssLoanModel;
      let docs = props.loanInfo.fssLoanDocumentModels;

      return  propsToFields(loan);

    }else {

    }

  }

})(LoanListAddModal);
export default LoanListAddModal;
