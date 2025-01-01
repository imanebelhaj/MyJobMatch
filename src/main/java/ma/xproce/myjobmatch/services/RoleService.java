//package ma.xproce.myjobmatch.services;
//
//import ma.xproce.myjobmatch.dao.entities.Roles;
//import ma.xproce.myjobmatch.dao.repositories.RolesRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;

//// 🚩🚩🚩🚩 provides role management features for admin
//// 🚩🚩but roles are static in my case do i dont need it
//
//@Service
//public class RoleService {
//
//
//    @Autowired
//    private RolesRepository rolesRepository;
//
//    // Method to create a new role
////    public Roles createRole(String roleName) {
////        // Check if the role already exists
////        if (rolesRepository.existsByName(roleName)) {
////            throw new RuntimeException("Role already exists.");
////        }
////        Roles newRole = new Roles();
////        newRole.setName(roleName);
////        return rolesRepository.save(newRole);
////    }
//
//    // Method to get all roles
//    public List<Roles> getAllRoles() {
//        return rolesRepository.findAll();
//    }
//
//    // Method to get a role by name
////    public Roles getByName(String roleName) {
////        Roles role = rolesRepository.findByName(roleName);
////        return role.orElseThrow(() -> new RuntimeException("Role not found: " + roleName));
////    }
//
//    // Method to delete a role by name
////    public void deleteRole(String roleName) {
////        Roles role = getByName(roleName);
////        rolesRepository.delete(role);
////    }
//
//}
