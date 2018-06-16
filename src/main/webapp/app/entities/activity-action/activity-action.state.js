(function () {
  'use strict';

  angular
    .module('recruitsmartApp')
    .config(stateConfig);

  stateConfig.$inject = ['$stateProvider'];

  function stateConfig($stateProvider) {
    $stateProvider
      .state('activity-action', {
        parent: 'entity',
        url: '/activity-action',
        data: {
          authorities: ['ROLE_USER'],
          pageTitle: 'ActivityActions'
        },
        views: {
          'content@': {
            templateUrl: 'app/entities/activity-action/activity-actions.html',
            controller: 'ActivityActionController',
            controllerAs: 'vm'
          }
        },
        resolve: {}
      })
      .state('activity-action-detail', {
        parent: 'activity-action',
        url: '/activity-action/{id}',
        data: {
          authorities: ['ROLE_USER'],
          pageTitle: 'ActivityAction'
        },
        views: {
          'content@': {
            templateUrl: 'app/entities/activity-action/activity-action-detail.html',
            controller: 'ActivityActionDetailController',
            controllerAs: 'vm'
          }
        },
        resolve: {
          entity: ['$stateParams', 'ActivityAction', function ($stateParams, ActivityAction) {
            return ActivityAction.get({id: $stateParams.id}).$promise;
          }],
          previousState: ["$state", function ($state) {
            var currentStateData = {
              name: $state.current.name || 'activity-action',
              params: $state.params,
              url: $state.href($state.current.name, $state.params)
            };
            return currentStateData;
          }]
        }
      })
      .state('activity-action-detail.edit', {
        parent: 'activity-action-detail',
        url: '/detail/edit',
        data: {
          authorities: ['ROLE_USER']
        },
        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
          $uibModal.open({
            templateUrl: 'app/entities/activity-action/activity-action-dialog.html',
            controller: 'ActivityActionDialogController',
            controllerAs: 'vm',
            backdrop: 'static',
            size: 'lg',
            resolve: {
              entity: ['ActivityAction', function (ActivityAction) {
                return ActivityAction.get({id: $stateParams.id}).$promise;
              }]
            }
          }).result.then(function () {
            $state.go('^', {}, {reload: false});
          }, function () {
            $state.go('^');
          });
        }]
      })
      .state('activity-action.new', {
        parent: 'activity-action',
        url: '/new',
        data: {
          authorities: ['ROLE_USER']
        },
        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
          $uibModal.open({
            templateUrl: 'app/entities/activity-action/activity-action-dialog.html',
            controller: 'ActivityActionDialogController',
            controllerAs: 'vm',
            backdrop: 'static',
            size: 'lg',
            resolve: {
              entity: function () {
                return {
                  name: null,
                  description: null,
                  id: null
                };
              }
            }
          }).result.then(function () {
            $state.go('activity-action', null, {reload: 'activity-action'});
          }, function () {
            $state.go('activity-action');
          });
        }]
      })
      .state('activity-action.edit', {
        parent: 'activity-action',
        url: '/{id}/edit',
        data: {
          authorities: ['ROLE_USER']
        },
        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
          $uibModal.open({
            templateUrl: 'app/entities/activity-action/activity-action-dialog.html',
            controller: 'ActivityActionDialogController',
            controllerAs: 'vm',
            backdrop: 'static',
            size: 'lg',
            resolve: {
              entity: ['ActivityAction', function (ActivityAction) {
                return ActivityAction.get({id: $stateParams.id}).$promise;
              }]
            }
          }).result.then(function () {
            $state.go('activity-action', null, {reload: 'activity-action'});
          }, function () {
            $state.go('^');
          });
        }]
      })
      .state('activity-action.delete', {
        parent: 'activity-action',
        url: '/{id}/delete',
        data: {
          authorities: ['ROLE_USER']
        },
        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
          $uibModal.open({
            templateUrl: 'app/entities/activity-action/activity-action-delete-dialog.html',
            controller: 'ActivityActionDeleteController',
            controllerAs: 'vm',
            size: 'md',
            resolve: {
              entity: ['ActivityAction', function (ActivityAction) {
                return ActivityAction.get({id: $stateParams.id}).$promise;
              }]
            }
          }).result.then(function () {
            $state.go('activity-action', null, {reload: 'activity-action'});
          }, function () {
            $state.go('^');
          });
        }]
      });
  }

})();
