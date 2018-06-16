(function () {
  'use strict';

  angular
    .module('recruitsmartApp')
    .config(stateConfig);

  stateConfig.$inject = ['$stateProvider'];

  function stateConfig($stateProvider) {
    $stateProvider
      .state('hot-list', {
        parent: 'entity',
        url: '/hot-list',
        data: {
          authorities: ['ROLE_USER'],
          pageTitle: 'HotLists'
        },
        views: {
          'content@': {
            templateUrl: 'app/entities/hot-list/hot-lists.html',
            controller: 'HotListController',
            controllerAs: 'vm'
          }
        },
        resolve: {}
      })
      .state('hot-list-detail', {
        parent: 'hot-list',
        url: '/hot-list/{id}',
        data: {
          authorities: ['ROLE_USER'],
          pageTitle: 'HotList'
        },
        views: {
          'content@': {
            templateUrl: 'app/entities/hot-list/hot-list-detail.html',
            controller: 'HotListDetailController',
            controllerAs: 'vm'
          }
        },
        resolve: {
          entity: ['$stateParams', 'HotList', function ($stateParams, HotList) {
            return HotList.get({id: $stateParams.id}).$promise;
          }],
          previousState: ["$state", function ($state) {
            var currentStateData = {
              name: $state.current.name || 'hot-list',
              params: $state.params,
              url: $state.href($state.current.name, $state.params)
            };
            return currentStateData;
          }]
        }
      })
      .state('hot-list-detail.edit', {
        parent: 'hot-list-detail',
        url: '/detail/edit',
        data: {
          authorities: ['ROLE_USER']
        },
        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
          $uibModal.open({
            templateUrl: 'app/entities/hot-list/hot-list-dialog.html',
            controller: 'HotListDialogController',
            controllerAs: 'vm',
            backdrop: 'static',
            size: 'lg',
            resolve: {
              entity: ['HotList', function (HotList) {
                return HotList.get({id: $stateParams.id}).$promise;
              }]
            }
          }).result.then(function () {
            $state.go('^', {}, {reload: false});
          }, function () {
            $state.go('^');
          });
        }]
      })
      .state('hot-list.new', {
        parent: 'hot-list',
        url: '/new',
        data: {
          authorities: ['ROLE_USER']
        },
        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
          $uibModal.open({
            templateUrl: 'app/entities/hot-list/hot-list-dialog.html',
            controller: 'HotListDialogController',
            controllerAs: 'vm',
            backdrop: 'static',
            size: 'lg',
            resolve: {
              entity: function () {
                return {
                  name: null,
                  type: null,
                  id: null
                };
              }
            }
          }).result.then(function () {
            $state.go('hot-list', null, {reload: 'hot-list'});
          }, function () {
            $state.go('hot-list');
          });
        }]
      })
      .state('hot-list.edit', {
        parent: 'hot-list',
        url: '/{id}/edit',
        data: {
          authorities: ['ROLE_USER']
        },
        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
          $uibModal.open({
            templateUrl: 'app/entities/hot-list/hot-list-dialog.html',
            controller: 'HotListDialogController',
            controllerAs: 'vm',
            backdrop: 'static',
            size: 'lg',
            resolve: {
              entity: ['HotList', function (HotList) {
                return HotList.get({id: $stateParams.id}).$promise;
              }]
            }
          }).result.then(function () {
            $state.go('hot-list', null, {reload: 'hot-list'});
          }, function () {
            $state.go('^');
          });
        }]
      })
      .state('hot-list.delete', {
        parent: 'hot-list',
        url: '/{id}/delete',
        data: {
          authorities: ['ROLE_USER']
        },
        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
          $uibModal.open({
            templateUrl: 'app/entities/hot-list/hot-list-delete-dialog.html',
            controller: 'HotListDeleteController',
            controllerAs: 'vm',
            size: 'md',
            resolve: {
              entity: ['HotList', function (HotList) {
                return HotList.get({id: $stateParams.id}).$promise;
              }]
            }
          }).result.then(function () {
            $state.go('hot-list', null, {reload: 'hot-list'});
          }, function () {
            $state.go('^');
          });
        }]
      });
  }

})();
