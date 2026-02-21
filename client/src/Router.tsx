import { Routes, Route } from 'react-router'
import Home from './views/Home'
import DashboardLayout from './layouts/DashboardLayout'
import Login from './views/Login'
import ProtectedRoute from './middlewares/ProtectedRoute'

export default function Router() {
    return (
        <div>
            <Routes>
                <Route element={<ProtectedRoute type='private' />} >
                    <Route element={<DashboardLayout />}>
                        <Route path="/" element={< Home />} />
                    </Route>
                </Route>

                <Route element={<ProtectedRoute type='public' />} >
                    <Route path="/login" element={<Login />} />
                </Route>
                {/* <Route path="/register" element={<Register />} /> */}
            </Routes>
        </div >
    )
}
