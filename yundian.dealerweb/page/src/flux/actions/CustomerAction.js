
import alt from 'bases/Alt.js';

class CustomerAction {
  /**
   * 初始化列表
   * @param data
   * @returns {*}
   */
  initDataListInfo = (data)=> {
    return data;
  };

  /**
   * 提交查询
   * @param data
   * @returns {*}
   */
  querySubmit = (data)=> {
    return data;
  };

  /**
   * 修改页面索引
   * @param pager
   * @returns {*}
   */
  setPagination = (pager)=> {
    return pager;
  };

  /**
   * 打开添加模态框
   * @param data
   * @returns {*}
   */
  openAddModal =()=> {
    return null;
  };
  /**
   * 导入数据
   * @param data
   * @returns {*}
   */
  importCustomer =(data)=>{
    return data;
  }
  /**
   * 打开修改模态框
   * @param data
   * @returns {*}
   */
  openUpdateModal =(data)=> {
    return data;
  };

  addLoan =(data)=> {
    return data;
  };

 updateLoan =(data)=> {
    return data;
  };
  /**
   * 提交审核
   * @param data
   * @returns {*}
   */
  submitLoan =(data)=> {
    return data;
  };

  /**
   * 删除记录
   * @param data
   * @returns {*}
   */
  deleteRecord = (data)=> {
    return data;
  };

  /**
   *改变激活状态
   */
  changeStatus =(data)=> {
    return data;
  };
}

export default alt.createActions(CustomerAction);
