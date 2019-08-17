package com.zking.zkingedu.common.controller;

import com.zking.zkingedu.common.model.Emp;
import com.zking.zkingedu.common.service.EmpService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

import java.util.List;


@Controller
@Slf4j
public class EmpController {

    @Autowired
    private EmpService empService;

    /**
     *得到所有员工
     */
    @ResponseBody
    @RequestMapping("/getEmps")
    public Object getEmps(HttpServletRequest request) {
        String empName = request.getParameter("empName");
        if(empName==null)
            empName="";
        List<Emp> emps = empService.getemps("%"+empName+"%");
        return emps;
    }

    /**
     *删除员工
     */
    @Transactional
    @ResponseBody
    @RequestMapping("/delEmp")
    public Object delEmp(@RequestParam("empID") Integer id,HttpServletRequest request) {
        //获取当前登录用户，用户不能对自己进行删除和封禁操作
        Emp emp =(Emp) request.getSession().getAttribute("emp");
        if(emp.getEmpID()==id)
            return false;
        int i = empService.delByEmpID(id);
        i+= empService.delEmpRoleByEmpID(id);

        if(i>1) {
            return true;
        }
        else
            return false;
    }
    /**
     *修改员工
     */
    @Transactional
    @ResponseBody
    @RequestMapping("/updateEmp")
    public Object updateEmp(@RequestParam("empID") Integer empid,@RequestParam("empPassword") String password,@RequestParam("roleID")Integer roleid,HttpServletRequest request) {
        int i = empService.updateEmpByEmpID(empid, password);
        i += empService.updateEmpRoleByEmpID(empid,roleid);
        Emp emp =(Emp) request.getSession().getAttribute("emp");
        if(i>1) {
            return true;
        }
        else
            return false;
    }

    /**
     *添加员工
     */
    @Transactional
    @ResponseBody
    @RequestMapping("/addEmp")
    public Object addEmp(@RequestParam("empName") String empname,@RequestParam("empPassword") String password,HttpServletRequest request) {
        Emp emp = new Emp();
        emp.setEmpName(empname);
        emp.setEmpPassword(password);
        emp.setEmpState(0);
        int i = empService.addEmp(emp);
        Emp emp1 =(Emp) request.getSession().getAttribute("emp");
        if(i>1) {
            return true;
        }
        else
            return false;
    }


}
