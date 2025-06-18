import { BrowserRouter, Navigate, Route, Routes } from "react-router-dom";
import Navbar from "./components/common/Navbar";
import HomePage from "./components/home/HomePage";
import Footer from "./components/common/Footer";
import AllRoomsPage from "./components/booking_rooms/AllRoomsPage";
import FindBookingPage from "./components/booking_rooms/FindBookingPage";
import RoomDetailsPage from "./components/booking_rooms/RoomDetailsPage";
import LoginPage from "./components/auth/LoginPage";
import RegisterPage from "./components/auth/RegisterPage";
import ProfilePage from "./components/profile/ProfilePage";
import EditProfilePage from "./components/profile/EditProfilePage";
import AdminPage from "./components/admin/AdminPage";
import ManageRoomPage from "./components/admin/ManageRoomPage";
import EditRoomPage from "./components/admin/EditRoomPage";
import AddRoomPage from "./components/admin/AddRoomPage";
import ManageBookingsPage from "./components/admin/ManageBookingsPage";
import EditBookingPage from "./components/admin/EditBookingPage";
// import { ProtectedRoute, AdminRoute } from "./services/guard";


function App() {

  return (
    <BrowserRouter>
      <div className="App">
        <Navbar />
        <div className="content">
          <Routes>

            {/* Public Routes  */}
            <Route exact path="/" element={<HomePage />} />
            <Route exact path="/home" element={<HomePage />} />
            <Route path="/rooms" element={<AllRoomsPage />} />
            <Route path="/find-booking" element={<FindBookingPage />} />
            <Route exact path="/login" element={<LoginPage />} />
            <Route path="/register" element={<RegisterPage />} />

            {/* Authenticated Users Routes/ or ProtectedRoute component  */}
            <Route path="/room-details-book/:roomId" element={<RoomDetailsPage />} />
            <Route path="/profile" element={<ProfilePage />} />
            <Route path="edit-profile" element={<EditProfilePage />} />

            {/* Admin Auth Routes / or AdminRoute Component */}
            <Route path="*" element={<Navigate to="/login" />} />
            <Route path="/admin" element={<AdminPage/>}/>
            <Route path="/admin/manage-rooms" element={<ManageRoomPage/>}/>
            <Route path="/admin/edit-room/:roomId" element={<EditRoomPage/>}/>
            <Route path="/admin/add-room" element={<AddRoomPage/>}/>
            <Route path="/admin/manage-bookings" element={<ManageBookingsPage/>}/>
            <Route path="/admin/edit-booking/:bookingCode" element={<EditBookingPage/>}/>
          </Routes>
        </div>
        <Footer />
      </div>
    </BrowserRouter>
  )
}

export default App;