package eu.novobit.controller;

import eu.novobit.annotation.CtrlDef;
import eu.novobit.com.rest.controller.MappedCrudController;
import eu.novobit.dto.ApiPermissionDto;
import eu.novobit.dto.RequestContextDto;
import eu.novobit.dto.data.RoleInfoDto;
import eu.novobit.dto.data.RolePermissionDto;
import eu.novobit.exception.handler.ImsExceptionHandler;
import eu.novobit.mapper.ApiPermissionMapper;
import eu.novobit.mapper.RoleInfoMapper;
import eu.novobit.model.DistinctApiPermission;
import eu.novobit.model.RoleInfo;
import eu.novobit.repository.ApiPermissionRepository;
import eu.novobit.repository.AssoRoleInfoAccountRepository;
import eu.novobit.service.impl.RoleInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * The type Role info controller.
 */
@Slf4j
@Validated
@RestController
@RequestMapping(path = "/api/private/roleInfo")
@CtrlDef(handler = ImsExceptionHandler.class, mapper = RoleInfoMapper.class, service = RoleInfoService.class)
public class RoleInfoController extends MappedCrudController<RoleInfo, RoleInfoDto, RoleInfoDto, RoleInfoService> {

    @Autowired
    private ApiPermissionRepository apiPermissionRepository;
    @Autowired
    private ApiPermissionMapper apiPermissionMapper;
    @Autowired
    private AssoRoleInfoAccountRepository assoRoleInfoAccountRepository;

    @Override
    public RoleInfoDto afterFindById(RoleInfoDto roleInfoDto) {
        List<DistinctApiPermission> apiPermissions = apiPermissionRepository.findDistinctServiceNameAndObject();
        Map<String, RolePermissionDto> rolePermissionDtos = new HashMap<>();

        //build all role permission list
        apiPermissions.forEach(apiPermission ->
                createOrUpdateRolePermission(rolePermissionDtos, apiPermission, false, false, false)
        );

        roleInfoDto.getPermissions().forEach(apiPermissionDto -> {
            switch (apiPermissionDto.getRqType()) {
                case GET -> {
                    RolePermissionDto tmpRolePermissionDto = rolePermissionDtos.get(apiPermissionDto.getServiceName() + apiPermissionDto.getObject());
                    tmpRolePermissionDto.setRead(true);
                    rolePermissionDtos.put(apiPermissionDto.getServiceName() + apiPermissionDto.getObject(), tmpRolePermissionDto);
                }
                case POST, PUT -> {
                    RolePermissionDto tmpRolePermissionDto = rolePermissionDtos.get(apiPermissionDto.getServiceName() + apiPermissionDto.getObject());
                    tmpRolePermissionDto.setWrite(true);
                    rolePermissionDtos.put(apiPermissionDto.getServiceName() + apiPermissionDto.getObject(), tmpRolePermissionDto);
                }
                case DELETE -> {
                    RolePermissionDto tmpRolePermissionDto = rolePermissionDtos.get(apiPermissionDto.getServiceName() + apiPermissionDto.getObject());
                    tmpRolePermissionDto.setDelete(true);
                    rolePermissionDtos.put(apiPermissionDto.getServiceName() + apiPermissionDto.getObject(), tmpRolePermissionDto);
                }
            }

        });

        roleInfoDto.setRolePermission(rolePermissionDtos.values().stream().toList());
        return roleInfoDto;
    }

    @Override
    public RoleInfoDto beforeCreate(RoleInfoDto roleInfoDto) {
        List<ApiPermissionDto> apiPermissionDtos = getApiPermissionDtos(roleInfoDto);
        roleInfoDto.setPermissions(apiPermissionDtos);
        return super.beforeCreate(roleInfoDto);
    }

    @Override
    public RoleInfoDto beforeUpdate(Long id, RoleInfoDto roleInfoDto) {
        List<ApiPermissionDto> apiPermissionDtos = getApiPermissionDtos(roleInfoDto);
        roleInfoDto.setPermissions(apiPermissionDtos);
        return super.beforeUpdate(id, roleInfoDto);
    }

    private List<ApiPermissionDto> getApiPermissionDtos(RoleInfoDto roleInfoDto) {
        List<ApiPermissionDto> apiPermissionDtos = new ArrayList<>();
        roleInfoDto.getRolePermission().forEach(rolePermissionDto ->
        {
            apiPermissionDtos.addAll(apiPermissionMapper.listEntityToDto(apiPermissionRepository.
                    findAllByServiceNameAndObjectAndRqTypeIn(rolePermissionDto.getServiceName(), rolePermissionDto.getObjectName(), rolePermissionDto.getHTTPRequest())));
        });
        return apiPermissionDtos;
    }


    private void createOrUpdateRolePermission(Map<String, RolePermissionDto> rolePermissionDtos, DistinctApiPermission apiPermission, Boolean canRead, Boolean canWrite, Boolean canDelete) {
        if (rolePermissionDtos.containsKey(apiPermission.getServiceName() + apiPermission.getObject())) {
            RolePermissionDto rolePermission = rolePermissionDtos.get(apiPermission.getServiceName() + apiPermission.getObject());
            if (canRead != null) rolePermission.setRead(canRead);
            if (canWrite != null) rolePermission.setWrite(canWrite);
            if (canDelete != null) rolePermission.setDelete(canDelete);
        } else {
            RolePermissionDto rolePermission = RolePermissionDto.builder()
                    .serviceName(apiPermission.getServiceName())
                    .objectName(apiPermission.getObject())
                    .build();
            if (canRead != null) rolePermission.setRead(canRead);
            if (canWrite != null) rolePermission.setWrite(canWrite);
            if (canDelete != null) rolePermission.setDelete(canDelete);
            rolePermissionDtos.put(apiPermission.getServiceName() + apiPermission.getObject()
                    , rolePermission);
        }
    }

    @Override
    public List<RoleInfoDto> afterFindAllFull(RequestContextDto requestContext, List<RoleInfoDto> list) {
        if (!CollectionUtils.isEmpty(list)) {
            list.stream().forEach(roleInfoDto -> {
                roleInfoDto.setNumberOfUsers(assoRoleInfoAccountRepository.countAllById_RoleInfoCode(roleInfoDto.getCode()));
            });
        }
        return super.afterFindAllFull(requestContext, list);
    }

    @Override
    public List<RoleInfoDto> afterFindAll(RequestContextDto requestContext, List<RoleInfoDto> list) {
        if (!CollectionUtils.isEmpty(list)) {
            list.stream().forEach(roleInfoDto -> {
                roleInfoDto.setNumberOfUsers(assoRoleInfoAccountRepository.countAllById_RoleInfoCode(roleInfoDto.getCode()));
            });
        }
        return super.afterFindAll(requestContext, list);
    }
}
