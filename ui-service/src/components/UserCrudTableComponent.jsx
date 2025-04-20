import React from "react";
import { fetchUsers } from "../api/userService";
import { DataGrid } from '@mui/x-data-grid';
import { useState, useEffect } from "react";
import Box from '@mui/material/Box';


function UserCrudTable() {
  const [users, setUsers] = useState([]);

  useEffect(() => {
    fetchUsers()
      .then((data) => setUsers(data))
      .catch((err) => console.error(err));
  }, []);

  const columns = [ 
    { field: 'id', headerName: 'ID', width: 90 },
    { field: 'first_name', headerName: 'First name', width: 150, editable: true},
    { field: 'last_name', headerName: 'Last name', width: 150, editable: true},
    { field: 'email', headerName: 'Email', width: 150, editable: true},
    { field: 'role', headerName: 'Role', width: 150, editable: true}
  ]
  if (!users) return <p>Загрузка...</p>;

//   return(
//   <Box sx={{ height: 800, width: '100%' }}>
//     <DataGrid
//         rows={users}
//         columns={columns}
//         initialState={{
//             pagination: {
//               paginationModel: {
//                 pageSize: 5,
//               },
//             },
//           }}
//           pageSizeOptions={[5]}
//         checkboxSelection
//         disableRowSelectionOnClick
//     />
//   </Box>
//   )
// }

return(
  <Box sx={{ height: 800, width: '100%' }}>
    <DataGrid
        rows={users}
        columns={columns}
        initialState={{
            pagination: {
              paginationModel: {
                pageSize: 5,
              },
            },
          }}
          pageSizeOptions={[5]}
        checkboxSelection
        disableRowSelectionOnClick
    />
  </Box>
  )
}


export default UserCrudTable;
