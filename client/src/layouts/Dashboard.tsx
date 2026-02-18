import React from "react";
import { Outlet } from "react-router";
import Sidebar from "../components/layout/Sidebar";
import Header from "../components/layout/Header";
import { AppSidebar } from "../components/layout/AppSidebar";

const DashboardLayout: React.FC = () => {
  return (
    <div className="flex h-screen overflow-hidden">
      {/* SIDEBAR */}
      {/* <Sidebar/> */}
      <AppSidebar/>

      {/* MAIN */}
      <main className="relative flex flex-1 flex-col overflow-y-auto overflow-x-hidden">
        <div className="container relative h-full px-4 pt-10 md:py-10">
          {/* Header */}
          <Header />

          {/* Main Content */}
          <div className="mt-28">
            <Outlet />
          </div>
        </div>
      </main>
    </div>
  );
};

export default DashboardLayout;