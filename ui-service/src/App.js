import "./App.css";
import AppBar from "@mui/material/AppBar";
import Toolbar from "@mui/material/Toolbar";
import Typography from "@mui/material/Typography";

function App() {
    return (
        <AppBar position="static">
            <Toolbar>
                <Typography variant="h6">My Users</Typography>
            </Toolbar>
        </AppBar>
    );
}

export default App;
