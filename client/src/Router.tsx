import { Routes, Route } from 'react-router'
import Home from './views/Home'
import Login from './views/login'
import DashboardLayout from './layouts/Dashboard'

export default function Router() {
    return (
        <div>
            <Routes>
                {/* <Route element={<DashboardLayout />}>
                    <Route path="/dashboard" element={<Dashboard />} />
                    <Route path="/dashboard/users" element={<Users />} />
                    <Route path="/dashboard/Service" element={<Service />} />
                </Route> */}

                <Route element={<DashboardLayout />}>
                    <Route path="/" element={< Home />} />
                </Route>


                <Route path="/login" element={<Login />} />
                {/* <Route path="/register" element={<Register />} /> */}
            </Routes>
        </div >
    )
}
